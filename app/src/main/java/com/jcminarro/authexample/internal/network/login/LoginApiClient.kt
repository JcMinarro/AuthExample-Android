package com.jcminarro.authexample.internal.network.login

import arrow.core.Try
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.network.ApiClient
import com.jcminarro.authexample.internal.network.OAuth
import javax.inject.Inject

class LoginApiClient @Inject
constructor(endpoint: LoginEndpoint) : ApiClient<LoginEndpoint>(endpoint) {

    @Throws(APIIOException::class)
    fun login(username: String, password: String): OAuth =
            map(evaluateCall(loginCall(username, password)))

    fun loginFP(username: String, password: String): Try<OAuth> =
            evaluateCallFP(loginCall(username, password)).map {map(it)}

    private fun loginCall(username: String, password: String) = endpoint.login(LoginBody(username, password))
}
