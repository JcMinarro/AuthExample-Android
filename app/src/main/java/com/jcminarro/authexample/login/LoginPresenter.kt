package com.jcminarro.authexample.login

import com.jcminarro.authexample.internal.interactor.InteractorExecutor
import com.jcminarro.authexample.internal.navigator.Navigator
import com.jcminarro.authexample.internal.presenter.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(
        private val loginInteractor: LoginInteractor,
        private val loginFPInteractor: LoginFPInteractor,
        private val navigator: Navigator) : BasePresenter<LoginPresenter.View>() {

    override fun update() {
        super.update()
        showLoginStatus()
    }

    private fun showLoginStatus() {
        view.showLogin()
        view.hideLoading()
    }

    fun login(username: String, password: String) =
            performLoginIfNotEmptyCredential(username, password, this::performLogin)

    fun loginFP(username: String, password: String) =
            performLoginIfNotEmptyCredential(username, password, this::performLoginFP)

    private fun performLoginFP(username: String, password: String) {
        showLoadingStatus()
        loginFPInteractor.execute(Input(username, password), {onLoginError()}, this::onLoginSuccess)
    }

    private fun performLogin(username: String, password: String) {
        showLoadingStatus()
        execute<LoginInteractor.Input, Boolean, Exception>(loginInteractor,
                LoginInteractor.Input(username, password),
                object : InteractorExecutor.Callback<Boolean, Exception> {
                    override fun onSuccess(isLoggedIn: Boolean) = onLoginSuccess(isLoggedIn)
                    override fun onError(error: Exception) = onLoginError()
                })
    }

    private fun onLoginSuccess(isLoggedIn: Boolean) = if (isLoggedIn) {
        onLoggedIn()
    } else {
        onLoginError()
    }

    private fun performLoginIfNotEmptyCredential(
            username: String,
            password: String,
            loginFunction: (String, String) -> Unit) =
            if (username.isBlank() || password.isBlank()) {
                onLoginError()
            } else {
                loginFunction(username, password)
            }

    private fun showLoadingStatus() {
        view.showLoading()
        view.hideLogin()
    }

    private fun onLoginError() {
        showLoginStatus()
        view.showError()
    }

    private fun onLoggedIn() {
        view.close()
        navigator.navigateToQuote()
    }

    interface View : BasePresenter.View {

        fun close()
        fun showError()
        fun showLogin()
        fun hideLogin()
        fun showLoading()
        fun hideLoading()
    }
}
