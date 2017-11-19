package com.jcminarro.authexample;

import android.app.Application;

import com.jcminarro.authexample.internal.di.component.AppComponent;
import com.jcminarro.authexample.internal.di.component.DaggerAppComponent;
import com.jcminarro.authexample.internal.di.module.AppModule;

public class AuthExampleApplication extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return component;
    }
}
