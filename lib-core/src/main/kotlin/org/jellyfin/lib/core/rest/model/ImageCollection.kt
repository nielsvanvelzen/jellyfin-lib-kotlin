package org.jellyfin.lib.core.rest.model

import com.google.gson.annotations.SerializedName

data class ImageCollection(
	@SerializedName("Primary") val primary: String
)
