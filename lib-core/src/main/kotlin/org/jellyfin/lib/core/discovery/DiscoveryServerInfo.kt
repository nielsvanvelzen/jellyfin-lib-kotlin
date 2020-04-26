package org.jellyfin.lib.core.discovery

import com.google.gson.annotations.SerializedName

data class DiscoveryServerInfo(
	@SerializedName("Id") val id: String,
	@SerializedName("Address") val address: String,
	@SerializedName("Name") val name: String,
	@SerializedName("EndpointAddress") val endpointAddress: String?
)
