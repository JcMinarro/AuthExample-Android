package com.jcminarro.authexample.internal.network.login

import com.jcminarro.authexample.internal.network.ApiClient
import com.jcminarro.authexample.internal.network.OAuth
import javax.inject.Inject

class LoginApiClient @Inject
constructor(endpoint: LoginEndpoint) : ApiClient<LoginEndpoint>(endpoint) {

    fun login(username: String, password: String): OAuth =
            map(evaluateCall(endpoint.login(LoginBody(username, password)).execute()))
}
