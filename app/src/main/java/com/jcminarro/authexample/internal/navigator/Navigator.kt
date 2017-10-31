package com.jcminarro.authexample.internal.navigator

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.jcminarro.authexample.login.LoginActivity
import java.lang.ref.WeakReference
import javax.inject.Inject

class Navigator @Inject constructor(var appCompactActivity: AppCompatActivity) {
    val appCompactActivityWeakReference = WeakReference(appCompactActivity)

    fun navigateToLogin() {
        startActivity { LoginActivity.getLaunchIntent(it) }
    }

    fun navigateToMain() {
    }

    private fun startActivity(getIntentFunction: (context: Context) -> Intent) =
            appCompactActivityWeakReference.get()?.let {
                it.startActivity(getIntentFunction(it))
            }
}

