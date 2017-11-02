package com.jcminarro.authexample.internal.repository

import com.jcminarro.authexample.internal.model.Quote
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.network.quote.QuoteApiClient
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import org.amshove.kluent.Verify
import org.amshove.kluent.When
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Test

class QuoteRepositoryTest {

    private val quote = Quote("Linus Torvalds", "If you think your users are idiots only idiots will use it.")
    private val quoteApiClient: QuoteApiClient = mock()
    private val quoteRepository = QuoteRepository(quoteApiClient)

    @Test
    fun `Should return a random quote`() {
        When calling quoteApiClient.getRandomQuote() doReturn quote

        val result = quoteRepository.getRandomQuote()

        result.author `should equal to` quote.author
        result.message `should equal to` quote.message
        Verify on quoteApiClient that quoteApiClient.getRandomQuote() was called
    }

    @Test(expected = APIIOException::class)
    fun `Should throw an exception asking for a random quote`() {
        When calling quoteApiClient.getRandomQuote() doThrow APIIOException(mock())

        quoteRepository.getRandomQuote()

        Verify on quoteApiClient that quoteApiClient.getRandomQuote() was called
    }
}