package org.jellyfin.lib.core.rest.model

import com.google.gson.annotations.SerializedName

data class ServerInfo(
	val id: String,
	@SerializedName("ServerName") val name: String,
	@SerializedName("LocalAddress") val address: String,
	val version: String
)
