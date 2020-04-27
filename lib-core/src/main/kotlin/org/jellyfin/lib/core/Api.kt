package org.jellyfin.lib.core

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import org.jellyfin.lib.model.DeviceProfile

abstract class Api(
	private val jellyfin: Jellyfin,
	private val baseUrl: String,
	private val deviceProfile: DeviceProfile
) {
	private val client =  HttpClient {
		install(JsonFeature) {
			serializer = GsonSerializer(jellyfin.gson)
		}
	}

	protected fun createUrl(
		path: String,
		queryParameters: Map<String, String>
	) = URLBuilder().apply {
		// Create from base URL
		takeFrom(baseUrl)

		// Assign path
		encodedPath = "${encodedPath.trimEnd('/')}/${path.trimStart('/')}"

		// Append query parameters
		queryParameters.forEach {
			parameters.append(it.key, it.value)
		}
	}.build()

	protected fun createAuthorizationHeader(
		deviceProfile: DeviceProfile,
		accessToken: String? = null
	): String? {
		val params = mutableMapOf<String, String>()

		// DeviceProfile options
		params["Client"] = deviceProfile.client
		params["Device"] = deviceProfile.device
		params["DeviceId"] = deviceProfile.deviceId
		params["Version"] = deviceProfile.version

		// Authentication options
		accessToken?.let { params["Token"] = it }

		// Format: `MediaBrowser key="value", key="value"`
		return params.entries.joinToString(
			separator = ", ",
			prefix = "MediaBrowser ",
			transform = {
				// key="value"
				// todo does not use escaping
				"${it.key}=\"${it.value}\""
			})
	}

	private suspend fun request(
		method: HttpMethod = HttpMethod.Get,
		path: String = "/",
		queryParameters: Map<String, String> = emptyMap(),
		accessToken: String? = null,
		builder: HttpRequestBuilder.() -> Unit = {}
	): HttpResponse = client.request {
		this.method = method

		url(createUrl(path, queryParameters))

		val authorizationHeader = createAuthorizationHeader(deviceProfile, accessToken)
		if (authorizationHeader != null)
			header(AUTHORIZATION_HEADER, authorizationHeader)

		builder()
	}

	protected suspend fun get(
		path: String,
		queryParameters: Map<String, String> = emptyMap(),
		accessToken: String? = null
	) = request(
		method = HttpMethod.Get,
		path = path,
		queryParameters = queryParameters,
		accessToken = accessToken
	)

	protected suspend fun post(
		path: String,
		queryParameters: Map<String, String> = emptyMap(),
		body: Any? = null,
		accessToken: String? = null
	) = request(
		method = HttpMethod.Post,
		path = path,
		queryParameters = queryParameters,
		accessToken = accessToken,
		builder = {
			if (body != null) {
				contentType(ContentType.Application.Json)
				this.body = body
			}
		}
	)

	companion object {
		private const val AUTHORIZATION_HEADER: String = "x-emby-authorization"
	}
}
