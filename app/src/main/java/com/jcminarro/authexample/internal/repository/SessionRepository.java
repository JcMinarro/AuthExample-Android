package com.jcminarro.authexample.internal.repository;

import com.jcminarro.authexample.internal.localdatasource.SessionDatasource;
import com.jcminarro.authexample.internal.network.APIIOException;
import com.jcminarro.authexample.internal.network.OAuth;
import com.jcminarro.authexample.internal.network.login.LoginApiClient;
import com.jcminarro.authexample.internal.network.refresh.RefreshApiClient;

import javax.inject.Inject;

public class SessionRepository {

    private final LoginApiClient loginApiClient;
    private final RefreshApiClient refreshApiClient;
    private final SessionDatasource sessionDatasource;

    @Inject
    public SessionRepository(
            LoginApiClient loginApiClient,
            RefreshApiClient refreshApiClient,
            SessionDatasource sessionDatasource) {
        this.loginApiClient = loginApiClient;
        this.refreshApiClient = refreshApiClient;
        this.sessionDatasource = sessionDatasource;
    }

    public boolean login(String username, String password) throws APIIOException {
        sessionDatasource.storeOAuthSession(loginApiClient.login(username, password));
        return true;
    }

    public boolean refreshSession() {
        OAuth oAuth = sessionDatasource.getOAuthSession();
        if (oAuth != null) {
            try {
                sessionDatasource.storeOAuthSession(refreshApiClient.refresh(oAuth.getRefreshToken()));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
