package org.jellyfin.lib.core.discovery

import java.net.InetAddress
import java.net.NetworkInterface

class JavaNetBroadcastAddressesProvider : IDiscoveryBroadcastAddressesProvider {
	override suspend fun getBroadcastAddresses(): Collection<InetAddress> =
		NetworkInterface.getNetworkInterfaces().toList()
			.filter { !it.isLoopback && it.isUp }
			.flatMap { networkInterface ->
				networkInterface.interfaceAddresses.mapNotNull { address ->
					address.broadcast
				}
			}
}
