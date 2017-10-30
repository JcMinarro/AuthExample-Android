package com.jcminarro.authexample.internal.repository

import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.network.quote.QuoteApiClient

class QuoteRespository(private val quoteApiClient: QuoteApiClient) {

    @Throws(APIIOException::class)
    fun getRandomQuote() = quoteApiClient.getRandomQuote()
}