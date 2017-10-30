package com.jcminarro.authexample.internal.network.refresh

import com.jcminarro.authexample.internal.network.ApiClient
import com.jcminarro.authexample.internal.network.OAuth
import javax.inject.Inject

class RefreshApiClient @Inject
constructor(endpoint: RefreshEndpoint) : ApiClient<RefreshEndpoint>(endpoint) {

    fun refresh(refreshToken: String): OAuth =
            map(evaluateCall(endpoint.refreshTokens(RefreshBody(refreshToken)).execute()))
}