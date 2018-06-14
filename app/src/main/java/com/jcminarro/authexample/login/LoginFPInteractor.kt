package com.jcminarro.authexample.login

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.jcminarro.authexample.internal.interactor.FPAsyncInteractor
import com.jcminarro.authexample.internal.repository.SessionRepository
import javax.inject.Inject

class LoginFPInteractor @Inject constructor(private val sessionRepository: SessionRepository)
    : FPAsyncInteractor<Input, Boolean, CredentialError>() {
    override fun run(input: Input): Either<CredentialError, Boolean> = with(input) {
        sessionRepository.loginFP(username, password).fold({Left(CredentialError)}, {Right(it)})
    }
}

data class Input(val username: String, val password: String)
object CredentialError