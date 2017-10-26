package com.jcminarro.authexample.internal.di.injectablebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jcminarro.authexample.AuthExampleApplication;
import com.jcminarro.authexample.internal.di.component.ActivityComponent;
import com.jcminarro.authexample.internal.di.component.AppComponent;
import com.jcminarro.authexample.internal.presenter.BasePresenter;
import com.jcminarro.authexample.internal.presenter.lifecycle.PresenterLifecycleLinker;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseInjectionActivity<T extends ActivityComponent> extends AppCompatActivity implements
        BasePresenter.View {

    protected T activityComponent;
    @Inject PresenterLifecycleLinker presenterLifecycleLinker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDI();
        if (getLayout() > 0) {
            setContentView(getLayout());
            ButterKnife.bind(this);
            onConfigureViews();
        }
        onPreparePresenter();
        presenterLifecycleLinker.initialize(this);
    }

    protected void onConfigureViews() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterLifecycleLinker.updatePresenters(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenterLifecycleLinker.pausePresenters();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityComponent = null;
        presenterLifecycleLinker.destroyPresenters();
    }

    protected abstract void initDI();

    protected abstract int getLayout();

    public AppComponent getAppComponent() {
        return ((AuthExampleApplication) getApplication()).getAppComponent();
    }

    T getActivityComponent() {
        return activityComponent;
    }

    /**
     * Called before to initialize all the presenter instances linked to the component lifecycle.
     * Override this method to configure your presenter with extra data if needed.
     */
    protected void onPreparePresenter() {
    }

}