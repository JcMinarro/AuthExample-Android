package com.jcminarro.authexample.login

import arrow.core.Failure
import arrow.core.Success
import com.jcminarro.authexample.internal.network.APIIOException
import com.jcminarro.authexample.internal.repository.SessionRepository
import com.nhaarman.mockito_kotlin.doReturn
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

class LoginFPInteractorTest {

    private val VALID_USERNAME = "Jc Miñarro"
    private val VALID_PASSWORD = "password"
    private val INVALID_USERNAME = "username"
    private val INVALID_PASSWORD = "1234"
    private val sessionRepository: SessionRepository = mock()
    private val loginFPInteractor = LoginFPInteractor(sessionRepository)
    private val apiIoException = APIIOException(mock())
    private val onError: (CredentialError) -> Unit = mock()
    private val onSuccess: (Boolean) -> Unit = mock()

    @Before
    fun setUp() {
        When calling sessionRepository.loginFP(VALID_USERNAME, VALID_PASSWORD) doReturn Success(true)
        When calling sessionRepository.loginFP(INVALID_USERNAME, VALID_PASSWORD) doReturn Failure(apiIoException)
        When calling sessionRepository.loginFP(VALID_USERNAME, INVALID_PASSWORD) doReturn Failure(apiIoException)
    }

    @Test
    fun `Should login successful with valid credential`() {
        loginFPInteractor.execute(Input(VALID_USERNAME, VALID_PASSWORD), onError, onSuccess)

        Verify on sessionRepository that sessionRepository.loginFP(VALID_USERNAME, VALID_PASSWORD) was called
        Verify on onSuccess that onSuccess(true) was called
        `Verify no further interactions` on sessionRepository
        `Verify no further interactions` on onError
    }

    @Test
    fun `Should notify an error when try to login with an invalid password`() {
        loginFPInteractor.execute(Input(VALID_USERNAME, INVALID_PASSWORD), onError, onSuccess)

        Verify on sessionRepository that sessionRepository.loginFP(VALID_USERNAME, INVALID_PASSWORD) was called
        Verify on onError that onError(CredentialError) was called
        `Verify no further interactions` on sessionRepository
        `Verify no further interactions` on onSuccess
    }

    @Test
    fun `Should notify an error when try to login with an invalid username`() {
        loginFPInteractor.execute(Input(INVALID_USERNAME, VALID_PASSWORD), onError, onSuccess)

        Verify on sessionRepository that sessionRepository.loginFP(INVALID_USERNAME, VALID_PASSWORD) was called
        Verify on onError that onError(CredentialError) was called
        `Verify no further interactions` on sessionRepository
        `Verify no further interactions` on onSuccess
    }
}