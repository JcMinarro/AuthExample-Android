package com.jcminarro.authexample.login;

import android.text.TextUtils;

import com.jcminarro.authexample.internal.interactor.InteractorExecutor;
import com.jcminarro.authexample.internal.navigator.Navigator;
import com.jcminarro.authexample.internal.presenter.BasePresenter;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginPresenter.View> {

    private final LoginInteractor loginInteractor;
    private final Navigator navigator;

    @Inject
    public LoginPresenter(LoginInteractor loginInteractor, Navigator navigator) {
        this.loginInteractor = loginInteractor;
        this.navigator = navigator;
    }

    @Override
    public void update() {
        super.update();
        showLoginStatus();
    }

    private void showLoginStatus() {
        getView().showLogin();
        getView().hideLoading();
    }

    public void login(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            onLoginError();
        } else {
            performLogin(username, password);
        }
    }

    private void performLogin(String username, String password) {
        showLoadingStatus();
        execute(loginInteractor,
                new LoginInteractor.Input(username, password),
                new InteractorExecutor.Callback<Boolean, Exception>() {
                    @Override
                    public void onSuccess(Boolean isLoggedIn) {
                        if (isLoggedIn) {
                            onLoggedIn();
                        } else {
                            onLoginError();
                        }
                    }

                    @Override
                    public void onError(Exception error) {
                        onLoginError();
                    }
                });
    }

    private void showLoadingStatus() {
        getView().showLoading();
        getView().hideLogin();
    }

    private void onLoginError() {
        showLoginStatus();
        getView().showError();
    }

    private void onLoggedIn() {
        getView().close();
        navigator.navigateToMain();
    }

    interface View extends BasePresenter.View {

        void close();

        void showError();

        void showLogin();

        void hideLogin();

        void showLoading();

        void hideLoading();
    }
}
