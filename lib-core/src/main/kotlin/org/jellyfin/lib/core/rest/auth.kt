package org.jellyfin.lib.core.rest

import org.jellyfin.lib.core.Server
import org.jellyfin.lib.core.User
import org.jellyfin.lib.model.AuthenticateResponse
import org.jellyfin.lib.model.UsernamePassword

suspend fun Server.authenticateByName(username: String, password: String): User {
	val response = post<AuthenticateResponse>(
		path = "users/authenticateByName",
		body = UsernamePassword(username, password)
	)

	return getUser(response.userInfo.id, response.accessToken)
}
