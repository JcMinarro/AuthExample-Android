package com.jcminarro.authexample.internal.network.authorizator

import com.jcminarro.authexample.internal.network.AccessTokenProvider
import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.doReturn
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class AuthorizatedApiInterceptorTest {

    private val HEADER_AUTH_KEY = "X-access-token"
    private val response: Response = mock()
    private val request: Request = Request.Builder().url("http://jc.com").build()
    private val chain: Interceptor.Chain = mock()
    private val accessTokenProvider: AccessTokenProvider = mock()
    private val accessToken = "accessToken"
    private val authorizatedApiInterceptor = AuthorizatedApiInterceptor(accessTokenProvider)

    @Before
    fun setUp() {
        When calling chain.request() doReturn request
        When calling chain.proceed(any()) doReturn response
        When calling accessTokenProvider.accessToken doReturn accessToken
    }

    @Test
    fun `Should add a header with the access token`() {
        authorizatedApiInterceptor.intercept(chain)

        Verify on chain that chain.proceed(check {
            it.header(HEADER_AUTH_KEY) `should equal to` accessToken
        }) was called
    }
}