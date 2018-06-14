package com.jcminarro.authexample.internal.network.login

import arrow.core.Failure
import arrow.core.Success
import com.jcminarro.authexample.EndpointPath
import com.jcminarro.authexample.ResponseMother
import com.jcminarro.authexample.createLoginEndpoint
import com.jcminarro.authexample.createLoginResponse
import com.jcminarro.authexample.createLoginResponseJson
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.network.OAuth
import com.jcminarro.authexample.removeAllSpaces
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test

class LoginApiClientWithMockWebServerTest {

    val VALID_USERNAME = "JcMiñarro"
    val VALID_PASSWORD = "password"
    val INVALID_USERNAME = "username"
    val INVALID_PASSWORD = "1234"

    private lateinit var loginApiClient: LoginApiClient
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        loginApiClient = LoginApiClient(
                createLoginEndpoint(server.url("/").toString()))
        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse = when {
                isInvalidUsernameRequest(request) -> MockResponse().setResponseCode(401)
                isInvalidPasswordRequest(request) -> MockResponse().setResponseCode(401)
                isValidLoginRequest(request) -> MockResponse()
                        .setResponseCode(200)
                        .setBody(createLoginResponseJson(createLoginResponse()))
                else -> throw InterruptedException("Request not supported")
            }

            private fun isInvalidUsernameRequest(request: RecordedRequest): Boolean =
                    request.method == "POST" &&
                            request.path == EndpointPath.LOGIN &&
                            request.body.clone().readUtf8() == createRequestBodyJson(INVALID_USERNAME, VALID_PASSWORD).removeAllSpaces()

            private fun isInvalidPasswordRequest(request: RecordedRequest): Boolean =
                    request.method == "POST" &&
                            request.path == EndpointPath.LOGIN &&
                            request.body.clone().readUtf8() == createRequestBodyJson(VALID_USERNAME, INVALID_PASSWORD).removeAllSpaces()

            private fun isValidLoginRequest(request: RecordedRequest): Boolean =
                    request.method == "POST" &&
                            request.path == EndpointPath.LOGIN &&
                            request.body.clone().readUtf8() == createRequestBodyJson(VALID_USERNAME, VALID_PASSWORD).removeAllSpaces()
        }
        server.setDispatcher(dispatcher)
    }

    @Test
    fun `Should return an OAuth when login with valid credential`() {
        val oAuth = loginApiClient.login(VALID_USERNAME, VALID_PASSWORD)

        oAuth.accessToken `should equal to` ResponseMother.LOGIN_MOTHERR_accessToken
        oAuth.refreshToken `should equal to` ResponseMother.LOGIN_MOTHERR_refreshToken
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception when try to login with an invalid password`() {
        loginApiClient.login(VALID_USERNAME, INVALID_PASSWORD)
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception when try to login with an invalid username`() {
        loginApiClient.login(INVALID_USERNAME, VALID_PASSWORD)
    }

    @Test
    fun `Should return a Success of OAuth when login with valid credential`() {
        val oAuth = loginApiClient.loginFP(VALID_USERNAME, VALID_PASSWORD)

        oAuth `should equal` Success(OAuth(ResponseMother.LOGIN_MOTHERR_accessToken, ResponseMother.LOGIN_MOTHERR_refreshToken))
    }

    @Test()
    fun `Should return Failure of OAuth when try to login with an invalid password`() {
        val oAuth = loginApiClient.loginFP(VALID_USERNAME, INVALID_PASSWORD)

        oAuth `should be instance of` Failure::class
        (oAuth as Failure<OAuth>).exception `should be instance of` APIIOException::class
    }

    @Test()
    fun `Should return Failure of OAuth when try to login with an invalid username`() {
        val oAuth = loginApiClient.loginFP(INVALID_USERNAME, VALID_PASSWORD)

        oAuth `should be instance of` Failure::class
        (oAuth as Failure<OAuth>).exception `should be instance of` APIIOException::class
    }

    fun createRequestBodyJson(username: String, password: String) =
            """
                {
                "username": "$username",
                "password": "$password"
                }
                """.trimIndent()
}