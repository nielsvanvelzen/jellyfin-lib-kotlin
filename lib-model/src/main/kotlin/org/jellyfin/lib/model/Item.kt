package org.jellyfin.lib.model

import com.google.gson.annotations.SerializedName

data class Item(
	val id: String,
	val name: String,
	val etag: String?, // extra field
	val type: String,
	@SerializedName("ImageTags") val images: ImageCollection,
	val isFolder: Boolean
)
