package com.jcminarro.authexample.internal.network.login

import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.network.ApiClient
import com.jcminarro.authexample.internal.network.OAuth
import javax.inject.Inject

class LoginApiClient @Inject
constructor(endpoint: LoginEndpoint) : ApiClient<LoginEndpoint>(endpoint) {

    @Throws(APIIOException::class)
    fun login(username: String, password: String): OAuth =
            map(evaluateCall(endpoint.login(LoginBody(username, password)).execute()))
}
