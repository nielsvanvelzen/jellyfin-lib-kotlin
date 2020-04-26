package org.jellyfin.lib.core

data class DeviceProfile(
	val client: String,
	val deviceId: String,
	val device: String,
	val version: String
)
