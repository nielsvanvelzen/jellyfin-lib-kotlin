package org.jellyfin.lib.core.rest

import org.jellyfin.lib.core.Server
import org.jellyfin.lib.model.ServerInfo
import org.jellyfin.lib.model.UserInfo

suspend fun Server.systemInfoPublic(): ServerInfo = get("system/info/public")
suspend fun Server.usersPublic(): Set<UserInfo> = get("users/public")
