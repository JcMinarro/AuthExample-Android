package com.jcminarro.authexample.internal.localdatasource;

import android.content.SharedPreferences;

import com.jcminarro.authexample.internal.network.AccessTokenProvider;
import com.jcminarro.authexample.internal.network.OAuth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

public class SharedPreferenceSesssionDatasource implements SessionDatasource, AccessTokenProvider {

    private static final String ACCESS_TOKEN_KEY = "accessToken";
    private static final String REFRESH_TOKEN_KEY = "refreshToken";
    private final SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferenceSesssionDatasource(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void storeOAuthSession(@NotNull OAuth auth) {
        sharedPreferences.edit()
                .putString(ACCESS_TOKEN_KEY, auth.getAccessToken())
                .putString(REFRESH_TOKEN_KEY, auth.getRefreshToken())
                .apply();
    }

    @Nullable
    @Override
    public OAuth getOAuthSession() {
        String accessToken = sharedPreferences.getString(ACCESS_TOKEN_KEY, null);
        String refreshToken = sharedPreferences.getString(REFRESH_TOKEN_KEY, null);
        if (accessToken != null && refreshToken != null) {
            return new OAuth(accessToken, refreshToken);
        } else {
            return null;
        }
    }

    @Override
    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, "");
    }
}
