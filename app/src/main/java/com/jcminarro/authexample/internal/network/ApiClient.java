package com.jcminarro.authexample.internal.network;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class ApiClient<T> {

    protected final T endpoint;

    public ApiClient(T endpoint) {
        this.endpoint = endpoint;
    }

    protected <U> U evaluateCall(Call<U> call) throws IOException {
        Response<U> response = call.execute();
        if (!response.isSuccessful()) {
            throw new APIIOException(response.raw());
        }
        return response.body();
    }
}
