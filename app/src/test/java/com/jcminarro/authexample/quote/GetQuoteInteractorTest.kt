package com.jcminarro.authexample.quote

import com.jcminarro.authexample.internal.interactor.AsyncInteractor
import com.jcminarro.authexample.internal.model.Quote
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.repository.QuoteRepository
import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.spy
import org.amshove.kluent.*
import org.junit.Test

class GetQuoteInteractorTest {

    private val quoteRepository: QuoteRepository = mock()
    private val getQuoteInteractor = GetQuoteInteractor(quoteRepository)
    private val callback: AsyncInteractor.Callback<Quote, Exception> = spy()
    private val apiIoException = APIIOException(mock())
    private val quote = Quote("Linus Torvalds", "If you think your users are idiots only idiots will use it.")

    @Test
    fun `Should return a random quote`() {
        When calling quoteRepository.getRandomQuote() doReturn quote

        getQuoteInteractor.execute(null, callback)

        Verify on callback that callback.onSuccess(check {
            it.message `should equal to` quote.message
            it.author `should equal to` quote.author
        }) was called
        `Verify no further interactions` on callback
        Verify on quoteRepository that quoteRepository.getRandomQuote() was called
        `Verify no further interactions` on quoteRepository
    }

    @Test
    fun `Should notify an error asking for a random quote`() {
        When calling quoteRepository.getRandomQuote() doThrow apiIoException

        getQuoteInteractor.execute(null, callback)

        Verify on callback that callback.onError(apiIoException) was called
        `Verify no further interactions` on callback
        Verify on quoteRepository that quoteRepository.getRandomQuote() was called
        `Verify no further interactions` on quoteRepository
    }
}