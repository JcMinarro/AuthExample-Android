package com.jcminarro.authexample.quote;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jcminarro.authexample.R;
import com.jcminarro.authexample.internal.di.component.DaggerQuoteComponent;
import com.jcminarro.authexample.internal.di.component.QuoteComponent;
import com.jcminarro.authexample.internal.di.injectablebase.BaseInjectionActivity;
import com.jcminarro.authexample.internal.di.module.ActivityModule;
import com.jcminarro.authexample.internal.presenter.Presenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class QuoteActivity extends BaseInjectionActivity<QuoteComponent> implements QuotePresenter.View {

    @BindView(R.id.loading) View loading;
    @BindView(R.id.author) TextView authorView;
    @BindView(R.id.message) TextView messageView;

    @Inject
    @Presenter QuotePresenter presenter;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, QuoteActivity.class);
    }

    @Override
    protected void initDI() {
        activityComponent = DaggerQuoteComponent
                .builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
        activityComponent.inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_quote;
    }

    @Override
    public void showQuote(String author, String message) {
        authorView.setText(author);
        messageView.setText(message);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @OnClick(R.id.getQuote)
    public void onGetQuoteClick() {
        presenter.getRandomQuote();
    }
}
