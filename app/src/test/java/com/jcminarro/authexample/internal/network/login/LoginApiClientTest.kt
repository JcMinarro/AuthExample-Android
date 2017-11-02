package com.jcminarro.authexample.internal.network.login

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.jcminarro.authexample.EndpointMother
import com.jcminarro.authexample.EndpointPath
import com.jcminarro.authexample.ResponseMother
import com.jcminarro.authexample.createLoginEndpoint
import com.jcminarro.authexample.createLoginResponse
import com.jcminarro.authexample.createLoginResponseJson
import com.jcminarro.authexample.internal.network.APIIOException
import org.amshove.kluent.`should equal to`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginApiClientTest {

    val VALID_USERNAME = "Jc Miñarro"
    val VALID_PASSWORD = "password"
    val INVALID_USERNAME = "username"
    val INVALID_PASSWORD = "1234"

    private lateinit
    var loginApiClient: LoginApiClient

    @Rule
    @JvmField
    val wiremockRule = WireMockRule(WireMockConfiguration.options().dynamicPort())

    @Before
    fun setUp() {
        loginApiClient = LoginApiClient(
                createLoginEndpoint(EndpointMother.DEFAULT_API_HOST + wiremockRule.port()))
    }

    @Test
    fun `Should return an OAuth when login with valid credential`() {
        stubFor(post(EndpointPath.LOGIN)
                .withRequestBody(equalToJson(createRequestBodyJson(VALID_USERNAME, VALID_PASSWORD)))
                .willReturn(aResponse().withBody(
                        createLoginResponseJson(
                                createLoginResponse()))))

        val oAuth = loginApiClient.login(VALID_USERNAME, VALID_PASSWORD)

        oAuth.accessToken `should equal to` ResponseMother.LOGIN_MOTHERR_accessToken
        oAuth.refreshToken `should equal to` ResponseMother.LOGIN_MOTHERR_refreshToken
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception when try to login with an invalid password`() {
        stubFor(post(EndpointPath.LOGIN)
                .withRequestBody(equalToJson(createRequestBodyJson(VALID_USERNAME, INVALID_PASSWORD)))
                .willReturn(aResponse().withStatus(401)))

        loginApiClient.login(VALID_USERNAME, INVALID_PASSWORD)
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception when try to login with an invalid username`() {
        stubFor(post(EndpointPath.LOGIN)
                .withRequestBody(equalToJson(createRequestBodyJson(INVALID_USERNAME, VALID_PASSWORD)))
                .willReturn(aResponse().withStatus(401)))

        loginApiClient.login(INVALID_USERNAME, VALID_PASSWORD)
    }

    fun createRequestBodyJson(username: String, password: String) =
            """
                {
                "username": "$username",
                "password": "$password"
                }
                """.trimIndent()
}