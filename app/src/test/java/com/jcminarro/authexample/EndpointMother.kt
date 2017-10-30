package com.jcminarro.authexample

import com.jcminarro.authexample.internal.network.EndpointFactory
import com.jcminarro.authexample.internal.network.login.LoginEndpoint
import com.jcminarro.authexample.internal.network.refresh.RefreshEndpoint

object EndpointMother{

    const val DEFAULT_API_HOST = "http://localhost:"
}

fun createLoginEndpoint(apiHost: String) =
        EndpointFactory.Builder(apiHost)
                .build()
                .create(LoginEndpoint::class.java)

fun createRefeshEndpoint(apiHost: String) =
        EndpointFactory.Builder(apiHost)
                .build()
                .create(RefreshEndpoint::class.java)