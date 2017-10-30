package com.jcminarro.authexample.internal.network.quote

import com.jcminarro.authexample.internal.model.Quote
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.network.ApiClient
import javax.inject.Inject

class QuoteApiClient @Inject
constructor(endpoint: QuoteEndpoint) : ApiClient<QuoteEndpoint>(endpoint) {

    @Throws(APIIOException::class)
    fun getRandomQuote(): Quote =
            map(evaluateCall(endpoint.randomQuote.execute()))
}