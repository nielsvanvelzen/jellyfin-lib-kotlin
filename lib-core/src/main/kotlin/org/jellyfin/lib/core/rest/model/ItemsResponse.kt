package org.jellyfin.lib.core.rest.model

import com.google.gson.annotations.SerializedName

data class ItemsResponse(
	@SerializedName("Items") val items: List<Item>
)
