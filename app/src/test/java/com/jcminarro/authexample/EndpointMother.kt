package com.jcminarro.authexample

import com.jcminarro.authexample.EndpointMother.TOKENS_PROVIDER_MOTHER_accessToken
import com.jcminarro.authexample.EndpointMother.TOKENS_PROVIDER_MOTHER_refreshToken
import com.jcminarro.authexample.internal.network.EndpointFactory
import com.jcminarro.authexample.internal.network.TokensProvider
import com.jcminarro.authexample.internal.network.authorizator.AuthorizatedApiInterceptor
import com.jcminarro.authexample.internal.network.authorizator.UnauthorizatedApiInterceptor
import com.jcminarro.authexample.internal.network.login.LoginEndpoint
import com.jcminarro.authexample.internal.network.quote.QuoteEndpoint
import com.jcminarro.authexample.internal.network.refresh.RefreshEndpoint

object EndpointMother {

    const val DEFAULT_API_HOST = "http://localhost:"
    const val TOKENS_PROVIDER_MOTHER_accessToken = "accessToken"
    const val TOKENS_PROVIDER_MOTHER_refreshToken = "refreshToken"
}

fun createTokensProviders(accessToken: String = TOKENS_PROVIDER_MOTHER_accessToken,
                          refreshToken: String = TOKENS_PROVIDER_MOTHER_refreshToken) =
        object : TokensProvider {
            override fun getAccessToken(): String = accessToken
            override fun getRefreshToken(): String = refreshToken
        }

fun createAuthorizatedApiInterceptor(tokensProvider: TokensProvider = createTokensProviders()) =
        AuthorizatedApiInterceptor(tokensProvider)

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
                        authorizatedApiInterceptor: AuthorizatedApiInterceptor = createAuthorizatedApiInterceptor()) =
        EndpointFactory.Builder(apiHost)
                .withAuthorizatedApiInterceptor(authorizatedApiInterceptor)
                .build()
                .create(QuoteEndpoint::class.java)