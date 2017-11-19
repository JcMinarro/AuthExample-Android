package com.jcminarro.authexample.login

import com.jcminarro.authexample.internal.interactor.KAsyncInteractor
import com.jcminarro.authexample.internal.repository.SessionRepository
import kategory.Either
import kategory.Try
import kategory.effects.IO
import kategory.effects.asyncContext
import kategory.effects.ev
import kategory.effects.runAsync
import kategory.right
import javax.inject.Inject

class KLoginInteractor
@Inject constructor(val sessionRepository: SessionRepository) : KAsyncInteractor<Input, Boolean, Error>() {

    override fun run(input: Input): Either<Error, Boolean> {
        return Try {sessionRepository.login(input.username, input.password)}
                .fold(
                        {Either.Left(CredentialError())},
                        {Either.Right(it)})
    }
}

data class Input(val username: String, val password: String)
object Output
sealed class Error
class CredentialError : Error()