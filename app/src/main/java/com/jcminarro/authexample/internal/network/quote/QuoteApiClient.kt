package com.jcminarro.authexample.internal.network.quote

import com.jcminarro.authexample.internal.model.Quote
import com.jcminarro.authexample.internal.network.ApiClient
import javax.inject.Inject

class QuoteApiClient @Inject
constructor(endpoint: QuoteEndpoint) : ApiClient<QuoteEndpoint>(endpoint) {

    fun getRandomQuote(username: String, password: String): Quote =
            map(evaluateCall(endpoint.randomQuote.execute()))
}