package com.jcminarro.authexample.internal.interactor

import arrow.core.Either
import arrow.effects.DeferredK
import arrow.effects.unsafeRunSync

abstract class FPAsyncInteractor<I, O, E> {

    fun execute(input: I, onError: (E) -> Unit = {}, onSuccess: (O) -> Unit): Unit =
            DeferredK{run(input)}.unsafeRunSync().fold(onError, onSuccess)

    protected abstract fun run(input: I): Either<E, O>
}