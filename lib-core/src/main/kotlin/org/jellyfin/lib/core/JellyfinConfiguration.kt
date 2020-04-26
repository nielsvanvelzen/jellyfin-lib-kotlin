package org.jellyfin.lib.core

class JellyfinConfiguration {
	// Profile of the application / device
	lateinit var deviceProfile: DeviceProfile

	/**
	 * Allow HTTP and WS connections
	 */
	var allowInsecureConnections: Boolean = false
}
