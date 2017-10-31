package com.jcminarro.authexample.internal.di.component;

import com.jcminarro.authexample.internal.di.module.ActivityModule;
import com.jcminarro.authexample.internal.di.module.SessionModule;
import com.jcminarro.authexample.internal.di.scope.PerActivity;
import com.jcminarro.authexample.login.LoginActivity;

import dagger.Component;

@PerActivity
@Component(modules = {ActivityModule.class, SessionModule.class},
        dependencies = AppComponent.class)
public interface LoginComponent extends ActivityComponent {

    void inject(LoginActivity loginActivity);
}
