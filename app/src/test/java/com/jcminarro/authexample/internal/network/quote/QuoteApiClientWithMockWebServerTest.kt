package com.jcminarro.authexample.internal.network.quote

import com.jcminarro.authexample.*
import com.jcminarro.authexample.internal.network.APIIOException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.`should equal to`
import org.junit.Before
import org.junit.Test

class QuoteApiClientWithMockWebServerTest {

    private lateinit var server: MockWebServer
    private lateinit var quoteApiClient: QuoteApiClient

    @Before
    fun setUp() {
        server = MockWebServer()
        quoteApiClient = QuoteApiClient(
                createQuoteEndpoint(server.url("/").toString()))
    }

    @Test
    fun `Should return a Quote when asking for a random quote`() {
        server.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(createQuoteResponseJson(
                        createQuoteResponse())))

        val quote = quoteApiClient.getRandomQuote()

        quote.author `should equal to` ResponseMother.QUOTE_MOTHERR_author
        quote.message `should equal to` ResponseMother.QUOTE_MOTHERR_message
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception when try to get a quote and there is a network error`() {
        server.enqueue(MockResponse().setResponseCode(400))

        quoteApiClient.getRandomQuote()
    }
}