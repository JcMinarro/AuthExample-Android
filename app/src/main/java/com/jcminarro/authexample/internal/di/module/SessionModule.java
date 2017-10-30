package com.jcminarro.authexample.internal.di.module;

import com.jcminarro.authexample.internal.network.EndpointFactory;
import com.jcminarro.authexample.internal.network.authorizator.UnauthorizatedApiInterceptor;
import com.jcminarro.authexample.internal.network.login.LoginEndpoint;
import com.jcminarro.authexample.internal.network.refresh.RefreshEndpoint;

import dagger.Module;
import dagger.Provides;

@Module
public class SessionModule {

    @Provides
    LoginEndpoint provideLoginEndpoint(
            EndpointFactory.Builder builder,
            UnauthorizatedApiInterceptor unauthorizatedApiInterceptor) {
        return builder.withUnaouthorizatedApiInterceptor(unauthorizatedApiInterceptor)
                .build()
                .create(LoginEndpoint.class);
    }

    @Provides
    RefreshEndpoint provideRefreshEndpoint(
            EndpointFactory.Builder builder,
            UnauthorizatedApiInterceptor unauthorizatedApiInterceptor) {
        return builder.withUnaouthorizatedApiInterceptor(unauthorizatedApiInterceptor)
                .build()
                .create(RefreshEndpoint.class);
    }
}