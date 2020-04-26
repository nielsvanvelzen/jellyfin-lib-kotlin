package org.jellyfin.lib.core.rest.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
	@SerializedName("Id") val id: String
)
