package com.jcminarro.authexample.internal.di.injectablebase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcminarro.authexample.internal.di.component.ActivityComponent;
import com.jcminarro.authexample.internal.di.component.AppComponent;
import com.jcminarro.authexample.internal.di.component.FragmentComponent;
import com.jcminarro.authexample.internal.presenter.BasePresenter;
import com.jcminarro.authexample.internal.presenter.lifecycle.PresenterLifecycleLinker;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseInjectionFragment<T extends FragmentComponent>
        extends Fragment implements BasePresenter.View {

    protected T fragmentComponent;
    private ActivityComponent activityComponent;
    private AppComponent appComponent;
    @Inject PresenterLifecycleLinker presenterLifecycleLinker;

    @Override
    public void onPause() {
        super.onPause();
        presenterLifecycleLinker.pausePresenters();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseInjectionActivity) {
            activityComponent = ((BaseInjectionActivity) context).activityComponent;
            appComponent = ((BaseInjectionActivity) context).getAppComponent();
        } else {
            throw new IllegalArgumentException(
                    "This fragment need to be attached to a class that extend : " +
                            BaseInjectionActivity.class.getCanonicalName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterLifecycleLinker.updatePresenters(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDI();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View view;
        if (getFragmentLayout() > 0) {
            view = inflater.inflate(getFragmentLayout(), container, false);
            ButterKnife.bind(this, view);
            onConfigureViews();
        } else {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        onPreparePresenter();
        initializePresenterLifecycle();
        return view;
    }

    protected void onConfigureViews() {
    }

    /**
     * Called before to initialize all the presenter instances linked to the component lifecycle.
     * Override this method to configure your presenter with extra data if needed.
     */
    protected void onPreparePresenter() {

    }

    protected void initializePresenterLifecycle() {
        presenterLifecycleLinker.initialize(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        appComponent = null;
        activityComponent = null;
        fragmentComponent = null;
        presenterLifecycleLinker.destroyPresenters();
    }

    protected abstract int getFragmentLayout();

    protected abstract void initDI();

    protected ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    protected AppComponent getAppComponent() {
        return appComponent;
    }
}
