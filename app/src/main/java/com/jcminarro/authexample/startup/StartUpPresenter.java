package com.jcminarro.authexample.startup;

import com.jcminarro.authexample.internal.interactor.InteractorExecutor;
import com.jcminarro.authexample.internal.presenter.BasePresenter;

import javax.inject.Inject;

public class StartUpPresenter extends BasePresenter<StartUpPresenter.View> {

    private final RefreshSessionInteractor refreshSessionInteractor;

    @Inject
    public StartUpPresenter(RefreshSessionInteractor refreshSessionInteractor) {
        this.refreshSessionInteractor = refreshSessionInteractor;
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

    }

    private void navigateToMain() {

    }

    interface View extends BasePresenter.View {

        void close();
    }
}
