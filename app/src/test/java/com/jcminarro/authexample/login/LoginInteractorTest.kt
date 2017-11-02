package com.jcminarro.authexample.login

import com.jcminarro.authexample.internal.interactor.AsyncInteractor
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.repository.SessionRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
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

class LoginInteractorTest {

    private val VALID_USERNAME = "Jc Miñarro"
    private val VALID_PASSWORD = "password"
    private val INVALID_USERNAME = "username"
    private val INVALID_PASSWORD = "1234"
    private val sessionRepository: SessionRepository = mock()
    private val loginInteractor = LoginInteractor(sessionRepository)
    private val callback: AsyncInteractor.Callback<Boolean, Exception> = spy()
    private val apiIoException = APIIOException(mock())

    @Before
    fun setUp() {
        When calling sessionRepository.login(VALID_USERNAME, VALID_PASSWORD) doReturn true
        When calling sessionRepository.login(INVALID_USERNAME, VALID_PASSWORD) doThrow apiIoException
        When calling sessionRepository.login(VALID_USERNAME, INVALID_PASSWORD) doThrow apiIoException
    }

    @Test
    fun `Should login successful with valid credential`() {
        loginInteractor.execute(LoginInteractor.Input(VALID_USERNAME, VALID_PASSWORD), callback)

        Verify on sessionRepository that sessionRepository.login(VALID_USERNAME, VALID_PASSWORD) was called
        Verify on callback that callback.onSuccess(true) was called
        `Verify no further interactions` on sessionRepository
        `Verify no further interactions` on callback
    }

    @Test
    fun `Should notify an error when try to login with an invalid password`() {
        loginInteractor.execute(LoginInteractor.Input(VALID_USERNAME, INVALID_PASSWORD), callback)

        Verify on sessionRepository that sessionRepository.login(VALID_USERNAME, INVALID_PASSWORD) was called
        Verify on callback that callback.onError(apiIoException) was called
        `Verify no further interactions` on sessionRepository
        `Verify no further interactions` on callback
    }

    @Test
    fun `Should notify an error when try to login with an invalid username`() {
        loginInteractor.execute(LoginInteractor.Input(INVALID_USERNAME, VALID_PASSWORD), callback)

        Verify on sessionRepository that sessionRepository.login(INVALID_USERNAME, VALID_PASSWORD) was called
        Verify on callback that callback.onError(apiIoException) was called
        `Verify no further interactions` on sessionRepository
        `Verify no further interactions` on callback
    }
}