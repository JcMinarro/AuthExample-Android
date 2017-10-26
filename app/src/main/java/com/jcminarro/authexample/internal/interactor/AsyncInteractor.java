package com.jcminarro.authexample.internal.interactor;

public interface AsyncInteractor<I, O, E> {

    interface Callback<O, E> {

        void onSuccess(O output);

        void onError(E error);
    }

    void execute(I input, final Callback<O, E> callback);
}