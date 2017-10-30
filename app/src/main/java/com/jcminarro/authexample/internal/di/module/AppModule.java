package com.jcminarro.authexample.internal.di.module;

import android.content.Context;

import com.jcminarro.authexample.AuthExampleApplication;
import com.jcminarro.authexample.Environment;
import com.jcminarro.authexample.internal.interactor.InUiThreadDispatcherDecorator;
import com.jcminarro.authexample.internal.interactor.InteractorExecutor;
import com.jcminarro.authexample.internal.interactor.JobExecutor;
import com.jcminarro.authexample.internal.interactor.ReferenceRetainerDecorator;
import com.jcminarro.authexample.internal.interactor.ThreadExecutor;
import com.jcminarro.authexample.internal.localdatasource.SessionDatasource;
import com.jcminarro.authexample.internal.localdatasource.SharedPreferenceSesssionDatasource;
import com.jcminarro.authexample.internal.network.AccessTokenProvider;
import com.jcminarro.authexample.internal.network.EndpointFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String SHARED_PREFERENCES_SESSION = ".session";
    private AuthExampleApplication application;

    public AppModule(AuthExampleApplication application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    InteractorExecutor provideInteractorExecutor(
            ThreadExecutor threadExecutor,
            ReferenceRetainerDecorator referenceRetainerDecorator,
            InUiThreadDispatcherDecorator inUiThreadDispatcherDecorator) {
        return new InteractorExecutor(
                threadExecutor,
                referenceRetainerDecorator,
                inUiThreadDispatcherDecorator
        );
    }

    @Provides
    EndpointFactory.Builder provideEndpointFactoryBuilder() {
        return new EndpointFactory.Builder(Environment.ENDPOINT_BASE_URL);
    }

    @Provides
    @Singleton
    SharedPreferenceSesssionDatasource provideSharedPreferenceSesssionDatasource(Context context) {
        return new SharedPreferenceSesssionDatasource(
                context.getSharedPreferences(SHARED_PREFERENCES_SESSION, Context.MODE_PRIVATE));
    }

    @Provides
    AccessTokenProvider provideAccessTokenProvider(
            SharedPreferenceSesssionDatasource sharedPreferenceSesssionDatasource) {
        return sharedPreferenceSesssionDatasource;
    }

    @Provides
    SessionDatasource provideSessionDatasource(
            SharedPreferenceSesssionDatasource sharedPreferenceSesssionDatasource) {
        return sharedPreferenceSesssionDatasource;
    }
}
