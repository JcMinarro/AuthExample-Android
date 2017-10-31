package com.jcminarro.authexample.quote;

import com.jcminarro.authexample.internal.interactor.InteractorExecutor;
import com.jcminarro.authexample.internal.model.Quote;
import com.jcminarro.authexample.internal.presenter.BasePresenter;

import javax.inject.Inject;

public class QuotePresenter extends BasePresenter<QuotePresenter.View> {

    private final GetQuoteInteractor getQuoteInteractor;

    @Inject
    public QuotePresenter(GetQuoteInteractor getQuoteInteractor) {
        this.getQuoteInteractor = getQuoteInteractor;
    }

    @Override
    public void initialize() {
        super.initialize();
        getRandomQuote();
    }

    public void getRandomQuote() {
        getView().showLoading();
        execute(getQuoteInteractor, null, new InteractorExecutor.Callback<Quote, Exception>() {
            @Override
            public void onSuccess(Quote quote) {
                renderQuote(quote);
            }

            @Override
            public void onError(Exception error) {
                renderError();
            }
        });
    }

    private void renderError() {
        getView().hideLoading();
        getView().showError();
    }

    private void renderQuote(Quote quote) {
        getView().hideLoading();
        getView().showQuote(quote.getAuthor(), quote.getMessage());
    }

    interface View extends BasePresenter.View {

        void showQuote(String author, String message);

        void showError();

        void showLoading();

        void hideLoading();
    }
}
