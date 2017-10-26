package com.jcminarro.authexample.internal.network;

import retrofit2.Response;

public class ApiClient<T> {

    protected final T endpoint;

    public ApiClient(T endpoint) {
        this.endpoint = endpoint;
    }

    protected <U> U evaluateCall(Response<U> response) throws APIIOException {
        if (!response.isSuccessful()) {
            throw new APIIOException(response.raw());
        }
        return response.body();
    }
}
