package org.jellyfin.lib.core

import com.google.gson.Gson
import io.ktor.client.call.TypeInfo
import io.ktor.client.features.json.JsonSerializer
import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.readText

class GsonSerializer(private val gson: Gson) :
	JsonSerializer {
	override fun write(data: Any, contentType: ContentType): OutgoingContent =
		TextContent(gson.toJson(data), contentType)

	override fun read(type: TypeInfo, body: Input): Any {
		val text = body.readText()
		return gson.fromJson(text, type.reifiedType)
	}
}
