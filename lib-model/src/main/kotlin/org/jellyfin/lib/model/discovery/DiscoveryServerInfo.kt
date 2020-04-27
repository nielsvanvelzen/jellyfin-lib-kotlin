package org.jellyfin.lib.model.discovery

data class DiscoveryServerInfo(
	val id: String,
	val address: String,
	val name: String,
	val endpointAddress: String?
)
