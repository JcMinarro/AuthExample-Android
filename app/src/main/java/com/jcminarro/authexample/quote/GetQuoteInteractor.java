package com.jcminarro.authexample.quote;

import com.jcminarro.authexample.internal.interactor.AsyncInteractor;
import com.jcminarro.authexample.internal.model.Quote;
import com.jcminarro.authexample.internal.network.APIIOException;
import com.jcminarro.authexample.internal.repository.QuoteRepository;

import javax.inject.Inject;

public class GetQuoteInteractor implements AsyncInteractor<Void, Quote, Exception> {

    private final QuoteRepository quoteRepository;

    @Inject
    public GetQuoteInteractor(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public void execute(Void input, Callback<Quote, Exception> callback) {
        try {
            callback.onSuccess(quoteRepository.getRandomQuote());
        } catch (APIIOException e) {
            callback.onError(e);
        }
    }
}
