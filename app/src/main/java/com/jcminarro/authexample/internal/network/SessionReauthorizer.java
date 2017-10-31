package com.jcminarro.authexample.internal.network;

import com.jcminarro.authexample.internal.network.reauthorizate.Reauthorizer;
import com.jcminarro.authexample.internal.repository.SessionRepository;

import javax.inject.Inject;

public class SessionReauthorizer implements Reauthorizer {

    private final SessionRepository sessionRepository;

    @Inject
    public SessionReauthorizer(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void reauthorize() {
        sessionRepository.refreshSession();
    }
}
