package com.jcminarro.authexample.internal.network.reauthorizate

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.inOrder
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class ReauthorizatedApiInterceptorTest {

    private val validRequest: Request = Request.Builder().url("http://jc.com").build()
    private val invalidRequest: Request = Request.Builder().url("http://jc.com").build()
    private val chain: Interceptor.Chain = mock()
    private val validResponse: Response = mock()
    private val invalidResponse: Response = mock()
    private val reauthorizer: Reauthorizer = mock()
    private val reauthorizatedApiInterceptor = ReauthorizatedApiInterceptor(reauthorizer)

    @Before
    fun setUp() {
        When calling validResponse.code() doReturn 200
        When calling invalidResponse.code() doReturn 401
        When calling chain.proceed(validRequest) doReturn validResponse
        When calling chain.proceed(invalidRequest) doReturn invalidResponse
    }

    @Test
    fun `Shouldn't call to reauthorize receiving a valid response`() {
        When calling chain.request() doReturn validRequest

        reauthorizatedApiInterceptor.intercept(chain)

        inOrder(chain){
            verify(chain).request()
            verify(chain).proceed(validRequest)
        }
        `Verify no further interactions` on chain
        `Verify no interactions` on reauthorizer
    }

    @Test
    fun `Should call to reauthorize receiving a invalid response`() {
        When calling chain.request() doReturn listOf(invalidRequest, validRequest)

        reauthorizatedApiInterceptor.intercept(chain)

        inOrder(chain) {
            verify(chain).request()
            verify(chain).proceed(invalidRequest)
            verify(chain).request()
            verify(chain).proceed(validRequest)
        }
        `Verify no further interactions` on chain
        Verify on reauthorizer that reauthorizer.reauthorize() was called
    }
}