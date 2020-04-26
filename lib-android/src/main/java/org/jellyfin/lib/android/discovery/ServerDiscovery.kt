package org.jellyfin.lib.android.discovery

import android.content.Context
import android.net.wifi.WifiManager
import androidx.core.content.getSystemService
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.jellyfin.lib.android.BuildConfig
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.NetworkInterface
import java.time.Duration

class ServerDiscovery {
	/**
	 * Retrieve the broadcast address using the Android WifiManager
	 */
	private fun getBroadcastAddress(context: Context): InetAddress? {
		val wifi = context.getSystemService<WifiManager>() ?: return null
		val dhcp = wifi.dhcpInfo ?: return null
		val broadcast = dhcp.ipAddress and dhcp.netmask or dhcp.netmask.inv()
		val quads = ByteArray(4)
		for (k in 0..3) quads[k] = (broadcast shr k * 8).toByte()
		return InetAddress.getByAddress(quads)
	}

	/**
	 * Send our broadcast message to a given address
	 */
	private fun discoverAddress(socket: DatagramSocket, address: InetAddress) {
		println("Is $address Jellyfin?")

		val message = "who is JellyfinServer?".toByteArray()
		val packet = DatagramPacket(message, message.size, address, 7359)
		socket.send(packet)
	}

	/**
	 * Try reading and parsing messages sent to a given socket
	 */
	private fun receive(socket: DatagramSocket): DiscoveryServerInfo? {
		val buffer = ByteArray(1024) // Buffer to receive message in
		val packet = DatagramPacket(buffer, buffer.size)

		return try {
			socket.receive(packet)

			// Convert message to string
			val message = String(packet.data, 0, packet.length)

			// Read as JSON
			var info = Gson().fromJson(message, DiscoveryServerInfo::class.java)
			if (info.endpointAddress == null && socket.remoteSocketAddress != null)
				info = info.copy(endpointAddress = socket.remoteSocketAddress.toString())

			info
		} catch (_: Exception) {
			// Unable to receive/parse
			null
		}
	}

	fun discover(
		context: Context,
		timeout: Duration = Duration.ofSeconds(30)
	) = flow {
		// Add demo server for debug builds
		//todo remove?
		if (BuildConfig.DEBUG) {
			emit(
				DiscoveryServerInfo(
					"713dc3fe952b438fa70ed35e4ef0525a",
					"https://demo.jellyfin.org/stable",
					"Stable Demo",
					null
				)
			)

			emit(
				DiscoveryServerInfo(
					"713dc3fe952b438fa70ed35e4ef0525a",
					"https://demo.jellyfin.org/nightly",
					"Nightly Demo",
					null
				)
			)
		}

		val socket = DatagramSocket().apply {
			broadcast = true
			soTimeout = timeout.toMillis().toInt()
		}

		// Send
		getBroadcastAddress(context)?.let { discoverAddress(socket, it) }
		NetworkInterface.getNetworkInterfaces().toList()
			.filter { !it.isLoopback && it.isUp }
			.forEach { networkInterface ->
				networkInterface.interfaceAddresses.forEach { address ->
					address.broadcast?.let { discoverAddress(socket, it) }
				}
			}

		// Try reading incoming messages but with a maximum of 15
		for (i in 0..15) {
			if (socket.isClosed || !GlobalScope.isActive) break

			val info = receive(socket) ?: continue
			emit(info)
		}

		// End
		socket.close()
	}
}
