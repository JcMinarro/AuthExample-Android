package com.jcminarro.authexample.internal.di.module;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.jcminarro.authexample.internal.di.injectablebase.BaseInjectionActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseInjectionActivity baseInjectionActivity;

    public ActivityModule(BaseInjectionActivity baseInjectionActivity) {
        this.baseInjectionActivity = baseInjectionActivity;
    }

    @Provides
    public AppCompatActivity provideAppCompatActivity() {
        return baseInjectionActivity;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return baseInjectionActivity.getSupportFragmentManager();
    }

    @Provides
    Resources provideResources() {
        return baseInjectionActivity.getResources();
    }
}