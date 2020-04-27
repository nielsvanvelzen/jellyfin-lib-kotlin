package org.jellyfin.lib.core.discovery

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.jellyfin.lib.model.discovery.DiscoveryServerInfo
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.time.Duration

class ServerDiscovery(
	private val discoveryBroadcastAddressesProvider: IDiscoveryBroadcastAddressesProvider,
	private val gson: Gson
) {
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
			var info = gson.fromJson(message, DiscoveryServerInfo::class.java)
			if (info.endpointAddress == null && socket.remoteSocketAddress != null)
				info = info.copy(endpointAddress = socket.remoteSocketAddress.toString())

			info
		} catch (_: Exception) {
			// Unable to receive/parse
			null
		}
	}

	fun discover(timeout: Duration = Duration.ofSeconds(30)) = flow {
		val socket = DatagramSocket().apply {
			broadcast = true
			soTimeout = timeout.toMillis().toInt()
		}

		// Send
		val addresses = discoveryBroadcastAddressesProvider.getBroadcastAddresses()
		addresses.forEach { address ->
			discoverAddress(socket, address)
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

