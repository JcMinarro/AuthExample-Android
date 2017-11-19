package com.jcminarro.authexample.internal.interactor

import kategory.Either
import kategory.effects.IO

abstract class KAsyncInteractor<I, O, E> {

    fun execute(input: I): IO<Either<E, O>> = IO{run(input)}

    abstract protected fun run(input: I): Either<E, O>

    fun bla(input: I, a: (E) -> Unit, b: (O) -> Unit) {
        run(input).fold({a(it)}, {b(it)})
    }
}