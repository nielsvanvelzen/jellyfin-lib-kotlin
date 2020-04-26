package org.jellyfin.lib.core.rest.model

import com.google.gson.annotations.SerializedName

data class UsernamePassword(
	@SerializedName("Username") val username: String,
	@SerializedName("Pw") val password: String
)
