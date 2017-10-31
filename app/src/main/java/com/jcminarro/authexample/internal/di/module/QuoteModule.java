package com.jcminarro.authexample.internal.di.module;

import com.jcminarro.authexample.internal.network.EndpointFactory;
import com.jcminarro.authexample.internal.network.SessionReauthorizer;
import com.jcminarro.authexample.internal.network.authorizator.AuthorizatedApiInterceptor;
import com.jcminarro.authexample.internal.network.quote.QuoteEndpoint;
import com.jcminarro.authexample.internal.network.reauthorizate.ReauthorizatedApiInterceptor;
import com.jcminarro.authexample.internal.network.reauthorizate.Reauthorizer;

import dagger.Module;
import dagger.Provides;

@Module
public class QuoteModule {

    @Provides
    QuoteEndpoint provideQuoteEndpoint(
            EndpointFactory.Builder builder,
            AuthorizatedApiInterceptor authorizatedApiInterceptor,
            ReauthorizatedApiInterceptor reauthorizatedApiInterceptor) {
        return builder.withAuthorizatedApiInterceptor(authorizatedApiInterceptor)
                .withReauthorizatedApiInterceptor(reauthorizatedApiInterceptor)
                .build()
                .create(QuoteEndpoint.class);
    }

    @Provides
    Reauthorizer provideReauthorizer(SessionReauthorizer sessionReauthorizer) {
        return sessionReauthorizer;
    }
}