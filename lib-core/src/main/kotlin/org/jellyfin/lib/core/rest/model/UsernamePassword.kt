package org.jellyfin.lib.core.rest.model

import com.google.gson.annotations.SerializedName

data class UsernamePassword(
	 val username: String,
	@SerializedName("Pw") val password: String
)
