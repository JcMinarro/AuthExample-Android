package com.jcminarro.authexample.internal.di.component;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public interface ActivityComponent {

    AppCompatActivity provideAppCompatActivity();

    Resources provideResources();

    FragmentManager provideFragmentManager();
}
