package com.jcminarro.authexample.startup;

import com.jcminarro.authexample.internal.interactor.InteractorExecutor;
import com.jcminarro.authexample.internal.navigator.Navigator;
import com.jcminarro.authexample.internal.presenter.BasePresenter;

import javax.inject.Inject;

public class StartUpPresenter extends BasePresenter<StartUpPresenter.View> {

    private final RefreshSessionInteractor refreshSessionInteractor;
    private final Navigator navigator;

    @Inject
    public StartUpPresenter(RefreshSessionInteractor refreshSessionInteractor, Navigator navigator) {
        this.refreshSessionInteractor = refreshSessionInteractor;
        this.navigator = navigator;
    }

    @Override
    public void update() {
        super.update();
        execute(refreshSessionInteractor,
                null,
                new InteractorExecutor.SuccessCallback<Boolean, Exception>() {

                    @Override
                    public void onSuccess(Boolean isRefreshedSession) {
                        if (isRefreshedSession) {
                            navigateToMain();
                        } else {
                            navigateToLogin();
                        }
                        getView().close();
                    }
                });
    }

    private void navigateToLogin() {
        navigator.navigateToLogin();
    }

    private void navigateToMain() {
        navigator.navigateToMain();
    }

    interface View extends BasePresenter.View {

        void close();
    }
}
