package org.jellyfin.lib.core

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.jellyfin.lib.core.discovery.ServerDiscovery

class Jellyfin(initConfiguration: (JellyfinConfiguration.() -> Unit)? = null) {
	private val configuration = JellyfinConfiguration().apply { initConfiguration?.invoke(this) }
	internal val gson by lazy {
		GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
			.create()
	}
	val serverDiscovery by lazy {
		ServerDiscovery(
			configuration.discoveryBroadcastAddressesProvider,
			gson
		)
	}

	fun getServer(baseUrl: String): Server = Server(this, baseUrl, configuration.deviceProfile)
}
