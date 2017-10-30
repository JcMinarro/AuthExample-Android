package com.jcminarro.authexample.internal.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcminarro.authexample.UtilsKt;
import com.jcminarro.authexample.internal.network.authorizator.AuthorizatedApi;
import com.jcminarro.authexample.internal.network.authorizator.AuthorizatedApiInterceptor;
import com.jcminarro.authexample.internal.network.authorizator.UnauthorizatedApiInterceptor;
import com.jcminarro.authexample.internal.network.authorizator.UnauthorizatedApi;
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
    private AuthorizatedApiInterceptor authorizatedApiInterceptor;
    private UnauthorizatedApiInterceptor unauthorizatedApiInterceptor;

    private EndpointFactory(String apiHost,
            AuthorizatedApiInterceptor authorizatedApiInterceptor,
            UnauthorizatedApiInterceptor unauthorizatedApiInterceptor) {
        this.apiHost = apiHost;
        this.authorizatedApiInterceptor = authorizatedApiInterceptor;
        this.unauthorizatedApiInterceptor = unauthorizatedApiInterceptor;
    }

    public <T> T create(Class<T> api) {
        return new Retrofit.Builder()
                .baseUrl(apiHost)
                .client(getDefaultHttpClient(api))
                .addConverterFactory(getConverterFactory())
                .build()
                .create(api);
    }

    private Converter.Factory getConverterFactory() {
        Gson gson = new GsonBuilder().create();
        return GsonConverterFactory.create(gson);
    }

    private OkHttpClient getDefaultHttpClient(Class api) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectionPool(CONNECTION_POOL)
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        addLoggingInterceptor(builder);
        addAuthorizatorInterceptorIfNeeded(api, builder);
        addUnauthorizatorInterceptorIfNeeded(api, builder);
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

    private void addAuthorizatorInterceptorIfNeeded(Class api, OkHttpClient.Builder builder) {
        if (api.isAnnotationPresent(AuthorizatedApi.class)) {
            if (authorizatedApiInterceptor == null) {
                throw new IllegalStateException("To build a " + api.getName() + " that is annotated with " +
                        AuthorizatedApi.class.getName() + " you need to add a " +
                        AuthorizatedApiInterceptor.class.getName() + " to the " + EndpointFactory.class.getName());
            }
            builder.addInterceptor(authorizatedApiInterceptor);
        }
    }

    private void addUnauthorizatorInterceptorIfNeeded(Class api, OkHttpClient.Builder builder) {
        if (api.isAnnotationPresent(UnauthorizatedApi.class)) {
            if (unauthorizatedApiInterceptor == null) {
                throw new IllegalStateException("To build a " + api.getName() + " that is annotated with " +
                        UnauthorizatedApi.class.getName() + " you need to add a " +
                        UnauthorizatedApiInterceptor.class.getName() + " to the " + EndpointFactory.class.getName());
            }
            builder.addInterceptor(unauthorizatedApiInterceptor);
        }
    }

    public static class Builder {
        private String apiHost;
        private AuthorizatedApiInterceptor authorizatedApiInterceptor;
        private UnauthorizatedApiInterceptor unauthorizatedApiInterceptor;

        public Builder(String apiHost) {
            this.apiHost = apiHost;
        }

        public Builder withAuthorizatedApiInterceptor(AuthorizatedApiInterceptor authorizatedApiInterceptor) {
            this.authorizatedApiInterceptor = authorizatedApiInterceptor;
            return this;
        }

        public Builder withUnaouthorizatedApiInterceptor(UnauthorizatedApiInterceptor unauthorizatedApiInterceptor) {
            this.unauthorizatedApiInterceptor = unauthorizatedApiInterceptor;
            return this;
        }

        public EndpointFactory build() {
            return new EndpointFactory(apiHost, authorizatedApiInterceptor, unauthorizatedApiInterceptor);
        }
    }
}
