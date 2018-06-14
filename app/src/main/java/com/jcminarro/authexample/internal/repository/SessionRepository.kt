package com.jcminarro.authexample.internal.repository

import arrow.core.Try
import com.jcminarro.authexample.internal.localdatasource.SessionDatasource
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.network.login.LoginApiClient
import com.jcminarro.authexample.internal.network.refresh.RefreshApiClient
import javax.inject.Inject

class SessionRepository @Inject
constructor(
        private val loginApiClient: LoginApiClient,
        private val refreshApiClient: RefreshApiClient,
        private val sessionDatasource: SessionDatasource) {

    @Throws(APIIOException::class)
    fun login(username: String, password: String): Boolean {
        sessionDatasource.storeOAuthSession(loginApiClient.login(username, password))
        return true
    }

    fun loginFP(username: String, password: String): Try<Boolean> =
            loginApiClient.loginFP(username, password).map {
                sessionDatasource.storeOAuthSession(it)
                true
            }

    fun refreshSession(): Boolean {
        val oAuth = sessionDatasource.oAuthSession
        if (oAuth != null) {
            try {
                sessionDatasource.storeOAuthSession(refreshApiClient.refresh(oAuth.refreshToken))
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }
}
