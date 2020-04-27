package org.jellyfin.lib.core

import io.ktor.client.call.receive
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
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

	suspend fun getSocket() {
		// TODO: Test if this works
		// TODO: Create type-safe way to send & receive messages
		val socket = client.webSocketSession {
			url(createUrl("/socket", mapOf("deviceId" to deviceProfile.deviceId)))

//			val authorizationHeader = createAuthorizationHeader(deviceProfile, accessToken)
//			header(AUTHORIZATION_HEADER, authorizationHeader)
		}

		socket.send(Frame.Text("Test"))
	}
}
