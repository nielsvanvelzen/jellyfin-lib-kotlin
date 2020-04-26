package org.jellyfin.lib.core

import org.jellyfin.lib.core.discovery.IDiscoveryBroadcastAddressesProvider
import org.jellyfin.lib.core.discovery.JavaNetBroadcastAddressesProvider

class JellyfinConfiguration {
	// Profile of the application / device
	lateinit var deviceProfile: DeviceProfile

	var discoveryBroadcastAddressesProvider: IDiscoveryBroadcastAddressesProvider = JavaNetBroadcastAddressesProvider()

	/**
	 * Allow HTTP and WS connections
	 */
	var allowInsecureConnections: Boolean = false
}
