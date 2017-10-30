package com.jcminarro.authexample.internal.network.quote

import com.jcminarro.authexample.internal.model.Quote

fun map(quoteResponse: QuoteResponse) = Quote(quoteResponse.author, quoteResponse.message)