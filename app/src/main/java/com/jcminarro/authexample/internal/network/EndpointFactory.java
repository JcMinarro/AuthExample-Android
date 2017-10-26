package com.jcminarro.authexample.internal.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcminarro.authexample.UtilsKt;
import com.moczul.ok2curl.CurlInterceptor;
import com.moczul.ok2curl.logger.Loggable;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EndpointFactory {

    private static final int CONNECT_TIMEOUT_SECONDS = 60;
    private static final int READ_TIMEOUT_SECONDS = 60;
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();

    private final String apiHost;

    private EndpointFactory(String apiHost) {
        this.apiHost = apiHost;
    }

    public <T> T create(Class<T> api) {
        return new Retrofit.Builder()
                .baseUrl(apiHost)
                .client(getDefaultHttpClient())
                .addConverterFactory(getConverterFactory())
                .build()
                .create(api);
    }

    private Converter.Factory getConverterFactory() {
        Gson gson = new GsonBuilder().create();
        return GsonConverterFactory.create(gson);
    }

    private OkHttpClient getDefaultHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectionPool(CONNECTION_POOL)
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        addLoggingInterceptor(builder);
        return builder.build();
    }

    private void addLoggingInterceptor(OkHttpClient.Builder builder) {
        builder.addInterceptor(
                new HttpLoggingInterceptor(
                        new HttpLoggingInterceptor.Logger(){
                            @Override
                            public void log(String message) {
                                UtilsKt.log(message);
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY));
        builder.addNetworkInterceptor(new CurlInterceptor(new Loggable() {
            @Override
            public void log(String message) {
                UtilsKt.log("Curl", message);
            }
        }));
    }

    public static class Builder {
        private String apiHost;

        public Builder(String apiHost) {
            this.apiHost = apiHost;
        }

        public EndpointFactory build() {
            return new EndpointFactory(apiHost);
        }
    }
}
