package com.jcminarro.authexample.internal.repository

import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.network.quote.QuoteApiClient
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val quoteApiClient: QuoteApiClient) {

    @Throws(APIIOException::class)
    fun getRandomQuote() = quoteApiClient.getRandomQuote()
}