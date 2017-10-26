package com.jcminarro.authexample.internal.interactor;

public class ExecutorCallbackToInteractorCallbackAdapter<O, E> implements AsyncInteractor.Callback<O, E> {

    private final InteractorExecutor.Callback<O, E> callback;

    ExecutorCallbackToInteractorCallbackAdapter(InteractorExecutor.Callback<O, E> callback) {
        this.callback = callback;
    }

    @Override
    public final void onSuccess(O output) {
        callback.onSuccess(output);
    }

    @Override
    public void onError(E error) {
        callback.onError(error);
    }
}