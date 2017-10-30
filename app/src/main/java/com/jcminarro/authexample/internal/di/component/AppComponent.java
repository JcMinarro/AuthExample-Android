package com.jcminarro.authexample.internal.di.component;

import android.content.Context;

import com.jcminarro.authexample.internal.di.module.AppModule;
import com.jcminarro.authexample.internal.interactor.InteractorExecutor;
import com.jcminarro.authexample.internal.localdatasource.SessionDatasource;
import com.jcminarro.authexample.internal.network.AccessTokenProvider;
import com.jcminarro.authexample.internal.network.EndpointFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Context provideContext();

    InteractorExecutor provideInteractorExecutor();

    EndpointFactory.Builder provideEndpointFactoryBuilder();

    SessionDatasource provideSessionDatasource();

    AccessTokenProvider provideAccessTokenProvider();
}