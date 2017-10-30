package com.jcminarro.authexample.internal.di.component;

import com.jcminarro.authexample.internal.di.module.ActivityModule;
import com.jcminarro.authexample.internal.di.module.SessionModule;
import com.jcminarro.authexample.internal.di.scope.PerActivity;
import com.jcminarro.authexample.startup.StartUpActivity;

import dagger.Component;

@PerActivity
@Component(modules = {ActivityModule.class, SessionModule.class},
        dependencies = AppComponent.class)
public interface StartUpComponent extends ActivityComponent {

    void inject(StartUpActivity startUpActivity);
}
