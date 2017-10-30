package com.jcminarro.authexample.internal.navigator

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import java.lang.ref.WeakReference
import javax.inject.Inject

class Navigator @Inject constructor(var appCompactActivity: AppCompatActivity) {
    val appCompactActivityWeakReference = WeakReference(appCompactActivity)

    fun navigateToLogin() {
    }

    fun navigateToMain() {
    }

    internal fun startActivity(getIntentFunction: (context: Context) -> Intent) =
            appCompactActivityWeakReference.get()?.let {
                it.startActivity(getIntentFunction(it))
            }
}

