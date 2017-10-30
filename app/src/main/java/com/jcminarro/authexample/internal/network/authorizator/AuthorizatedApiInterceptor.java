package com.jcminarro.authexample.internal.network.authorizator;

import android.support.annotation.NonNull;

import com.jcminarro.authexample.internal.network.AccessTokenProvider;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizatedApiInterceptor implements Interceptor {

    private static final String HEADER_AUTH_KEY = "X-access-token";

    private final AccessTokenProvider accessTokenProvider;

    @Inject
    public AuthorizatedApiInterceptor(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        return chain.proceed(addAuthHeaderToken(chain.request()));
    }

    private Request addAuthHeaderToken(Request request) {
        return request
                .newBuilder()
                .addHeader(HEADER_AUTH_KEY, accessTokenProvider.getAccessToken())
                .build();
    }
}
