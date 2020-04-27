package org.jellyfin.lib.core

import org.jellyfin.lib.core.discovery.IDiscoveryBroadcastAddressesProvider
import org.jellyfin.lib.core.discovery.JavaNetBroadcastAddressesProvider
import org.jellyfin.lib.model.DeviceProfile

class JellyfinConfiguration {
	// Profile of the application / device
	lateinit var deviceProfile: DeviceProfile

	var discoveryBroadcastAddressesProvider: IDiscoveryBroadcastAddressesProvider = JavaNetBroadcastAddressesProvider()
}
