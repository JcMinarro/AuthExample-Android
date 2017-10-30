package com.jcminarro.authexample.startup;

import com.jcminarro.authexample.internal.interactor.AsyncInteractor;
import com.jcminarro.authexample.internal.repository.SessionRepository;

import javax.inject.Inject;

public class RefreshSessionInteractor implements AsyncInteractor<Void, Boolean, Exception>{

    private final SessionRepository sessionRepository;

    @Inject
    public RefreshSessionInteractor(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void execute(Void input, Callback<Boolean, Exception> callback) {
        callback.onSuccess(sessionRepository.refreshSession());
    }
}
