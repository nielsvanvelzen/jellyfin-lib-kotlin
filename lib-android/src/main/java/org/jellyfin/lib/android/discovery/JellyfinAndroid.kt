package org.jellyfin.lib.android.discovery

import android.content.Context
import android.provider.Settings
import org.jellyfin.lib.android.BuildConfig
import org.jellyfin.lib.core.DeviceProfile
import org.jellyfin.lib.core.JellyfinConfiguration
import java.util.*

private const val DEVICE_ID = "device_id"

fun JellyfinConfiguration.android(
	context: Context,

	// Profile
	clientName: String,
	clientVersion: String
) {
	val libraryStore = context.getSharedPreferences(
		BuildConfig.LIBRARY_PACKAGE_NAME,
		Context.MODE_PRIVATE
	)

	if (!libraryStore.contains(DEVICE_ID)) {
		libraryStore
			.edit()
			.putString(DEVICE_ID, UUID.randomUUID().toString())
			.apply()
	}

	val deviceName = Settings.Global.getString(context.contentResolver, Settings.Global.DEVICE_NAME)
	val deviceId = libraryStore.getString(DEVICE_ID, "")!!

	deviceProfile = DeviceProfile(
		client = clientName,
		deviceId = deviceId,
		device = deviceName,
		version = clientVersion
	)
}
