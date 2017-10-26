package com.jcminarro.authexample.internal.interactor;

public interface ExecutorCallbackDecorator {

    <O, E> InteractorExecutor.Callback<O, E> decorate(InteractorExecutor.Callback<O, E> callback);
}
