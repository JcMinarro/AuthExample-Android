package com.jcminarro.authexample.internal.network.refresh

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.jcminarro.authexample.*
import com.jcminarro.authexample.internal.network.APIIOException
import org.amshove.kluent.`should equal to`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RefreshApiClientTest{

    val VALID_REFRESH_TOKEN = "valid_refresh_token"
    val INVALID_REFRESH_TOKEN = "invalid_refresh_token"

    private lateinit
    var refreshApiClient: RefreshApiClient

    @Rule
    @JvmField
    val wiremockRule = WireMockRule(WireMockConfiguration.options().dynamicPort())

    @Before
    fun setUp() {
        refreshApiClient = RefreshApiClient(
                createRefeshEndpoint(EndpointMother.DEFAULT_API_HOST + wiremockRule.port()))
    }

    @Test
    fun `Should return an OAuth when refreshing token with a valid refresh token`() {
        stubFor(post(EndpointPath.REFRESH)
                .withRequestBody(equalToJson(createRequestBodyJson(VALID_REFRESH_TOKEN)))
                .willReturn(aResponse().withBody(
                        createRefreshResponseJson(
                                createRefreshResponse()))))

        val oAuth = refreshApiClient.refresh(VALID_REFRESH_TOKEN)

        oAuth.accessToken `should equal to` ResponseMother.REFRESH_MOTHERR_accessToken
        oAuth.refreshToken `should equal to` ResponseMother.REFRESH_MOTHERR_refreshToken
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception when try to refresh token with an invalid refresh token`() {
        stubFor(post(EndpointPath.REFRESH)
                .withRequestBody(equalToJson(createRequestBodyJson(INVALID_REFRESH_TOKEN)))
                .willReturn(aResponse().withStatus(401)))

        refreshApiClient.refresh(INVALID_REFRESH_TOKEN)
    }

    private fun createRequestBodyJson(refreshToken: String) =
            """
                {
                "refreshToken": "$refreshToken"
                }
            """.trimIndent()
}