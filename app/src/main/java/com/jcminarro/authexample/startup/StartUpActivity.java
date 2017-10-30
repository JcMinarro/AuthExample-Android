package com.jcminarro.authexample.startup;

import com.jcminarro.authexample.internal.di.component.DaggerStartUpComponent;
import com.jcminarro.authexample.internal.di.component.StartUpComponent;
import com.jcminarro.authexample.internal.di.injectablebase.BaseInjectionActivity;
import com.jcminarro.authexample.internal.di.module.ActivityModule;
import com.jcminarro.authexample.internal.presenter.Presenter;

import javax.inject.Inject;

public class StartUpActivity extends BaseInjectionActivity<StartUpComponent>
        implements StartUpPresenter.View {

    @Inject
    @Presenter StartUpPresenter presenter;

    @Override
    protected void initDI() {
        activityComponent = DaggerStartUpComponent
                .builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
        activityComponent.inject(this);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public void close() {
        onBackPressed();
    }
}
