package org.jellyfin.lib.core.rest.model

import com.google.gson.annotations.SerializedName

data class AuthenticateResponse(
	val accessToken: String,
	@SerializedName("User") val userInfo: UserInfo
)
