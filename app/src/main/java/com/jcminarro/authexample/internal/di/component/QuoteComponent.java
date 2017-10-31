package com.jcminarro.authexample.internal.di.component;

import com.jcminarro.authexample.internal.di.module.ActivityModule;
import com.jcminarro.authexample.internal.di.module.QuoteModule;
import com.jcminarro.authexample.internal.di.module.SessionModule;
import com.jcminarro.authexample.internal.di.scope.PerActivity;
import com.jcminarro.authexample.quote.QuoteActivity;

import dagger.Component;

@PerActivity
@Component(modules = {ActivityModule.class,
        SessionModule.class,
        QuoteModule.class},
        dependencies = AppComponent.class)
public interface QuoteComponent extends ActivityComponent {

    void inject(QuoteActivity quoteActivity);
}
