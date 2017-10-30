package com.jcminarro.authexample.internal.network.reauthorizate;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReauthorizatedApiInterceptor implements Interceptor {

    private static final int UNAUTHORIZED_HTTP_CODE = 401;
    private final Reauthorizer reauthorizer;

    public ReauthorizatedApiInterceptor(Reauthorizer reauthorizer) {
        this.reauthorizer = reauthorizer;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == UNAUTHORIZED_HTTP_CODE) {
            reauthorizer.reauthorize();
            response = chain.proceed(chain.request());
        }
        return response;
    }
}
