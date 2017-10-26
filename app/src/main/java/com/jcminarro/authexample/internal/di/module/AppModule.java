package com.jcminarro.authexample.internal.di.module;

import android.content.Context;

import com.jcminarro.authexample.AuthExampleApplication;
import com.jcminarro.authexample.internal.interactor.InUiThreadDispatcherDecorator;
import com.jcminarro.authexample.internal.interactor.InteractorExecutor;
import com.jcminarro.authexample.internal.interactor.JobExecutor;
import com.jcminarro.authexample.internal.interactor.ReferenceRetainerDecorator;
import com.jcminarro.authexample.internal.interactor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

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
}
