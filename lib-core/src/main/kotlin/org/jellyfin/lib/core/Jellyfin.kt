package org.jellyfin.lib.core

class Jellyfin(initConfiguration: (JellyfinConfiguration.() -> Unit)? = null) {
	private val configuration = JellyfinConfiguration().apply { initConfiguration?.invoke(this) }

	fun getServer(baseUrl: String): Server = Server(baseUrl, configuration.deviceProfile)
}
