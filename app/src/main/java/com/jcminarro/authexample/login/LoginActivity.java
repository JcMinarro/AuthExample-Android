package com.jcminarro.authexample.login;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jcminarro.authexample.R;
import com.jcminarro.authexample.internal.di.component.DaggerLoginComponent;
import com.jcminarro.authexample.internal.di.component.LoginComponent;
import com.jcminarro.authexample.internal.di.injectablebase.BaseInjectionActivity;
import com.jcminarro.authexample.internal.di.module.ActivityModule;
import com.jcminarro.authexample.internal.presenter.Presenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseInjectionActivity<LoginComponent> implements LoginPresenter.View {

    @BindView(R.id.username) AutoCompleteTextView username;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.login_progress) View loading;
    @BindView(R.id.login_form) View loginForm;

    @Inject
    @Presenter LoginPresenter presenter;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onConfigureViews() {
        super.onConfigureViews();
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    onLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initDI() {
        activityComponent = DaggerLoginComponent
                .builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
        activityComponent.inject(this);
    }

    private void onLogin() {
        presenter.login(username.getText().toString(),
                password.getText().toString());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.signIn)
    public void onLoginClick() {
        onLogin();
    }

    @Override
    public void close() {
        onBackPressed();
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_invalid_credential, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLogin() {
        loginForm.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLogin() {
        loginForm.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }
}