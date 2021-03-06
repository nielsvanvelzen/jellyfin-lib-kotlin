package org.jellyfin.lib.core

import io.ktor.client.call.receive
import org.jellyfin.lib.model.DeviceProfile

class Server(
	val jellyfin: Jellyfin,
	val baseUrl: String,
	val deviceProfile: DeviceProfile
) : Api(jellyfin, baseUrl, deviceProfile) {
	suspend inline fun <reified T> get(
		path: String = "/",
		queryParameters: Map<String, String> = emptyMap()
	) = get(
		path = path,
		queryParameters = queryParameters,
		accessToken = null
	).receive<T>()

	suspend inline fun <reified T> post(
		path: String = "/",
		queryParameters: Map<String, String> = emptyMap(),
		body: Any? = null
	) = post(
		path = path,
		queryParameters = queryParameters,
		body = body,
		accessToken = null
	).receive<T>()

	/**
	 * Get a user within this server by id + token
	 */
	fun getUser(userId: String, accessToken: String) = User(jellyfin, this, userId, accessToken)
}
