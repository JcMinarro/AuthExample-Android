package com.jcminarro.authexample.login;

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

    public void login(String username, String password) {
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

    private void onLoginError() {
        getView().showError();
    }

    private void onLoggedIn() {
        getView().close();
        navigator.navigateToMain();
    }

    interface View extends BasePresenter.View {

        void close();

        void showError();
    }
}
