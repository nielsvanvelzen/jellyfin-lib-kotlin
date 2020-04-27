package org.jellyfin.lib.core

import io.ktor.client.call.receive

class User(
	val jellyfin: Jellyfin,
	val server: Server,
	val userId: String,
	val accessToken: String
) : Api(jellyfin, server.baseUrl, server.deviceProfile) {
	suspend inline fun <reified T> get(
		path: String = "/",
		queryParameters: Map<String, String> = emptyMap()
	) = get(
		path = path,
		queryParameters = queryParameters,
		accessToken = accessToken
	).receive<T>()

	suspend inline fun <reified T> post(
		path: String = "/",
		queryParameters: Map<String, String> = emptyMap(),
		body: Any? = null
	) = post(
		path = path,
		queryParameters = queryParameters,
		body = body,
		accessToken = accessToken
	).receive<T>()
}
