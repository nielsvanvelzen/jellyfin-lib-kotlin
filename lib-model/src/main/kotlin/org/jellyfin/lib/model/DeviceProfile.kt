package org.jellyfin.lib.model

data class DeviceProfile(
	val client: String,
	val deviceId: String,
	val device: String,
	val version: String
)
