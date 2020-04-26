package org.jellyfin.lib.core.rest.model

import com.google.gson.annotations.SerializedName

data class ServerInfo(
	@SerializedName("Id") val id: String,
	@SerializedName("ServerName") val name: String,
	@SerializedName("LocalAddress") val address: String,
	@SerializedName("Version") val version: String
)
