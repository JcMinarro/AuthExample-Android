package com.jcminarro.authexample.internal.network.quote

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.jcminarro.authexample.*
import com.jcminarro.authexample.internal.network.APIIOException
import org.amshove.kluent.`should equal to`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuoteApiClientTest {

    private lateinit
    var quoteApiClient: QuoteApiClient

    @Rule
    @JvmField
    val wiremockRule = WireMockRule(WireMockConfiguration.options().dynamicPort())

    @Before
    fun setUp() {
        quoteApiClient = QuoteApiClient(
                createQuoteEndpoint(EndpointMother.DEFAULT_API_HOST + wiremockRule.port()))
    }

    @Test
    fun `Should return a Quote when asking for a random quote`() {
        stubFor(get(EndpointPath.QUOTE)
                .willReturn(aResponse().withBody(
                        createQuoteResponseJson(
                                createQuoteResponse()))))

        val quote = quoteApiClient.getRandomQuote()

        quote.author `should equal to` ResponseMother.QUOTE_MOTHERR_author
        quote.message `should equal to` ResponseMother.QUOTE_MOTHERR_message
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception when try to get a quote and there is a network error`() {
        stubFor(get(EndpointPath.QUOTE)
                .willReturn(aResponse().withStatus(400)))

        quoteApiClient.getRandomQuote()
    }
}