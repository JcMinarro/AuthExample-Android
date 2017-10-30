package com.jcminarro.authexample

import com.jcminarro.authexample.EndpointMother.TOKENS_PROVIDER_MOTHER_accessToken
import com.jcminarro.authexample.internal.network.AccessTokenProvider
import com.jcminarro.authexample.internal.network.EndpointFactory
import com.jcminarro.authexample.internal.network.authorizator.AuthorizatedApiInterceptor
import com.jcminarro.authexample.internal.network.authorizator.UnauthorizatedApiInterceptor
import com.jcminarro.authexample.internal.network.login.LoginEndpoint
import com.jcminarro.authexample.internal.network.quote.QuoteEndpoint
import com.jcminarro.authexample.internal.network.reauthorizate.ReauthorizatedApiInterceptor
import com.jcminarro.authexample.internal.network.reauthorizate.Reauthorizer
import com.jcminarro.authexample.internal.network.refresh.RefreshEndpoint

object EndpointMother {

    const val DEFAULT_API_HOST = "http://localhost:"
    const val TOKENS_PROVIDER_MOTHER_accessToken = "accessToken"
}

fun createTokensProviders(accessToken: String = TOKENS_PROVIDER_MOTHER_accessToken) = AccessTokenProvider { accessToken }

fun createReauthorizer() = Reauthorizer {}

fun createReauthorizedApiInterceptor(reauthorizer: Reauthorizer = createReauthorizer()) =
        ReauthorizatedApiInterceptor(reauthorizer)

fun createAuthorizatedApiInterceptor(accessTokenProvider: AccessTokenProvider = createTokensProviders()) =
        AuthorizatedApiInterceptor(accessTokenProvider)

fun createUnathorizatedApiInterceptor() = UnauthorizatedApiInterceptor()

fun createLoginEndpoint(apiHost: String,
                        unauthorizatedApiInterceptor: UnauthorizatedApiInterceptor = createUnathorizatedApiInterceptor()) =
        EndpointFactory.Builder(apiHost)
                .withUnaouthorizatedApiInterceptor(unauthorizatedApiInterceptor)
                .build()
                .create(LoginEndpoint::class.java)

fun createRefeshEndpoint(apiHost: String,
                         unauthorizatedApiInterceptor: UnauthorizatedApiInterceptor = createUnathorizatedApiInterceptor()) =
        EndpointFactory.Builder(apiHost)
                .withUnaouthorizatedApiInterceptor(unauthorizatedApiInterceptor)
                .build()
                .create(RefreshEndpoint::class.java)

fun createQuoteEndpoint(apiHost: String,
                        authorizatedApiInterceptor: AuthorizatedApiInterceptor = createAuthorizatedApiInterceptor(),
                        reauthorizatedApiInterceptor: ReauthorizatedApiInterceptor = createReauthorizedApiInterceptor()) =
        EndpointFactory.Builder(apiHost)
                .withAuthorizatedApiInterceptor(authorizatedApiInterceptor)
                .withReauthorizatedApiInterceptor(reauthorizatedApiInterceptor)
                .build()
                .create(QuoteEndpoint::class.java)