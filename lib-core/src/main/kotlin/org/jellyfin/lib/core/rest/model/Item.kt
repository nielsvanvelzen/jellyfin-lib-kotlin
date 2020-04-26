package org.jellyfin.lib.core.rest.model

import com.google.gson.annotations.SerializedName

data class Item(
	@SerializedName("Id") val id: String,
	@SerializedName("Name") val name: String,
	@SerializedName("Etag") val etag: String?, // extra field
	@SerializedName("Type") val type: String,
	@SerializedName("ImageTags") val images: ImageCollection,
	@SerializedName("IsFolder") val isFolder: Boolean
) {
	fun isPhotoAlbum() = type == "PhotoAlbum"
	fun isPhoto() = type == "Photo"

	fun couldContainPhotos() = isPhotoAlbum() || type == "CollectionFolder"
}
