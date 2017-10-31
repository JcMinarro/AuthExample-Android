package com.jcminarro.authexample.login;

import com.jcminarro.authexample.internal.interactor.AsyncInteractor;
import com.jcminarro.authexample.internal.network.APIIOException;
import com.jcminarro.authexample.internal.repository.SessionRepository;

import javax.inject.Inject;

public class LoginInteractor implements AsyncInteractor<LoginInteractor.Input, Boolean, Exception> {

    private final SessionRepository sessionRepository;

    @Inject
    public LoginInteractor(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void execute(Input input, Callback<Boolean, Exception> callback) {
        try {
            callback.onSuccess(sessionRepository.login(input.username, input.password));
        } catch (APIIOException e) {
            callback.onError(e);
        }
    }

    public static class Input {
        private final String username;
        private final String password;

        public Input(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
