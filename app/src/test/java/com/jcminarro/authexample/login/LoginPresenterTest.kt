package com.jcminarro.authexample.login

import arrow.core.Failure
import arrow.core.Success
import com.jcminarro.authexample.internal.navigator.Navigator
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.repository.SessionRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.spy
import org.amshove.kluent.Verify
import org.amshove.kluent.When
import org.amshove.kluent.`Verify no further interactions`
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Before
import org.junit.Test

class LoginPresenterTest {

    private val VALID_USERNAME = "Jc Miñarro"
    private val VALID_PASSWORD = "password"
    private val INVALID_USERNAME = "username"
    private val INVALID_PASSWORD = "1234"
    private val apiIoException = APIIOException(mock())
    private val loginInteractor: LoginInteractor = mock()
    private val sessionRepository: SessionRepository = mock()
    private val loginFPInteractor = LoginFPInteractor(sessionRepository)
    private val navigator: Navigator = mock()
    private val view: LoginPresenter.View = spy()
    private val loginPresenter = LoginPresenter(loginInteractor, loginFPInteractor, navigator)

    @Before
    fun setUp() {
        loginPresenter.view = view
        When calling sessionRepository.loginFP(VALID_USERNAME, VALID_PASSWORD) doReturn Success(true)
        When calling sessionRepository.loginFP(INVALID_USERNAME, VALID_PASSWORD) doReturn Failure(apiIoException)
        When calling sessionRepository.loginFP(VALID_USERNAME, INVALID_PASSWORD) doReturn Failure(apiIoException)
    }

    @Test
    fun `Should login successful with valid credential`() {
        loginPresenter.loginFP(VALID_USERNAME, VALID_PASSWORD)

        Verify on view that view.showLoading() was called
        Verify on view that view.hideLogin() was called
        Verify on view that view.close() was called
        `Verify no further interactions` on view
        Verify on navigator that navigator.navigateToQuote() was called
    }

    @Test
    fun `Should notify an error when try to login with an invalid password`() {
        loginPresenter.loginFP(VALID_USERNAME, INVALID_PASSWORD)

        Verify on view that view.showLoading() was called
        Verify on view that view.hideLogin() was called
        Verify on view that view.showLogin() was called
        Verify on view that view.hideLoading() was called
        Verify on view that view.showError() was called
        `Verify no further interactions` on view
    }

    @Test
    fun `Should notify an error when try to login with an invalid username`() {
        loginPresenter.loginFP(INVALID_USERNAME, VALID_PASSWORD)

        Verify on view that view.showLoading() was called
        Verify on view that view.hideLogin() was called
        Verify on view that view.showLogin() was called
        Verify on view that view.hideLoading() was called
        Verify on view that view.showError() was called
        `Verify no further interactions` on view
    }
}