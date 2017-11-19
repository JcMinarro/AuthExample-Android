package com.jcminarro.authexample.login

import android.text.TextUtils
import com.jcminarro.authexample.internal.interactor.InteractorExecutor
import com.jcminarro.authexample.internal.navigator.Navigator
import com.jcminarro.authexample.internal.presenter.BasePresenter
import kategory.Either
import kategory.effects.IO
import javax.inject.Inject

class LoginPresenter @Inject
constructor(private val loginInteractor: LoginInteractor, private val kLoginInteractor: KLoginInteractor, private val navigator: Navigator) :
        BasePresenter<LoginPresenter.View>() {

    override fun update() {
        super.update()
        showLoginStatus()
    }

    private fun showLoginStatus() {
        view.showLogin()
        view.hideLoading()
    }

    fun login(username: String, password: String) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            onLoginError()
        } else {
            //performLogin(username, password)
        }
        //kLoginInteractor.bla(Input(username, password), {}, {})
        runIO(kLoginInteractor.execute(Input(username, password)), {print(it)}, {print(it)})
    }

    private fun <R, E> runIO(io: IO<Either<E, R>>, onError: (E) -> Unit, onSuccess: (R) -> Unit) {
        println("Into runIO")
        io.runAsync {
            println("Into runAsync")
            it.fold(
                    {
                        println("Into first fold")
                        IO{}},
                    {IO{it.fold(
                            {
                                println("Into Error second fold")
                                onError(it)},
                            {
                                println("Into Success second fold")
                                onSuccess(it)})}})
        }
    }

    private fun performLogin(username: String, password: String) {
        showLoadingStatus()
        execute(loginInteractor,
                LoginInteractor.Input(username, password),
                object : InteractorExecutor.Callback<Boolean, Exception> {
                    override fun onSuccess(isLoggedIn: Boolean?) {
                        if (isLoggedIn!!) {
                            onLoggedIn()
                        } else {
                            onLoginError()
                        }
                    }

                    override fun onError(error: Exception) {
                        onLoginError()
                    }
                })
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
