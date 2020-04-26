package org.jellyfin.lib.core.discovery

import java.net.InetAddress

interface IDiscoveryBroadcastAddressesProvider {
	/**
	 * Provide broadcast addresses
	 */
	suspend fun getBroadcastAddresses(): Collection<InetAddress>
}
