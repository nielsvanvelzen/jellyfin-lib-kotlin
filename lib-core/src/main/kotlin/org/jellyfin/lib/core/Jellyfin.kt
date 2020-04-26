package org.jellyfin.lib.core

import com.google.gson.GsonBuilder
import org.jellyfin.lib.core.discovery.ServerDiscovery

class Jellyfin(initConfiguration: (JellyfinConfiguration.() -> Unit)? = null) {
	private val configuration = JellyfinConfiguration().apply { initConfiguration?.invoke(this) }
	private val gson by lazy { GsonBuilder().create() }
	val serverDiscovery by lazy { ServerDiscovery(configuration.discoveryBroadcastAddressesProvider, gson) }

	fun getServer(baseUrl: String): Server = Server(baseUrl, configuration.deviceProfile)
}
