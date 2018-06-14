package com.jcminarro.authexample.internal.network.login

import arrow.core.Failure
import arrow.core.Success
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
import com.jcminarro.authexample.internal.network.OAuth
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should equal`
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
        val invalidLoginResponse = aResponse().withStatus(401)
        loginApiClient = LoginApiClient(
                createLoginEndpoint(EndpointMother.DEFAULT_API_HOST + wiremockRule.port()))
        stubFor(post(EndpointPath.LOGIN)
                .withRequestBody(equalToJson(createRequestBodyJson(VALID_USERNAME, VALID_PASSWORD)))
                .willReturn(aResponse().withBody(
                        createLoginResponseJson(
                                createLoginResponse()))))
        stubFor(post(EndpointPath.LOGIN)
                .withRequestBody(equalToJson(createRequestBodyJson(VALID_USERNAME, INVALID_PASSWORD)))
                .willReturn(invalidLoginResponse))
        stubFor(post(EndpointPath.LOGIN)
                .withRequestBody(equalToJson(createRequestBodyJson(INVALID_USERNAME, VALID_PASSWORD)))
                .willReturn(invalidLoginResponse))
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