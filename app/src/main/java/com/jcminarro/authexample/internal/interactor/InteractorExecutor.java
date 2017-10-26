package com.jcminarro.authexample.internal.interactor;

import java.util.Arrays;
import java.util.List;

public class InteractorExecutor {

    public interface Callback<O, E> {
        void onSuccess(O output);

        void onError(E error);
    }

    public static abstract class SuccessCallback<O, E> implements Callback<O, E> {

        @Override
        public void onError(E error) {
        }
    }

    public static class NullCallback<O, E> implements Callback<O, E> {

        @Override
        public void onSuccess(O output) {
        }

        @Override
        public void onError(E error) {
        }
    }

    private final ThreadExecutor threadExecutor;
    private final List<ExecutorCallbackDecorator> callbackDecorators;

    public InteractorExecutor(
            ThreadExecutor threadExecutor,
            ExecutorCallbackDecorator... decorators) {
        this.threadExecutor = threadExecutor;
        this.callbackDecorators = Arrays.asList(decorators);
    }

    public <I, O, E> void execute(
            final AsyncInteractor<I, O, E> interactor,
            final I input,
            Callback<O, E> callback) {
        final AsyncInteractor.Callback<O, E> wrappedCallback = adaptCallback(callback);
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                interactor.execute(input, wrappedCallback);
            }
        });
    }

    private <O, E> AsyncInteractor.Callback<O, E> adaptCallback(Callback<O, E> callback) {
        Callback<O, E> adaptedCallback = decorateCallback(callback);
        return new ExecutorCallbackToInteractorCallbackAdapter<>(adaptedCallback);
    }

    private <O, E> Callback<O, E> decorateCallback(Callback<O, E> callback) {
        Callback<O, E> decoratedCallback = callback;

        for (ExecutorCallbackDecorator adapter : callbackDecorators) {
            decoratedCallback = adapter.decorate(decoratedCallback);
        }

        return decoratedCallback;
    }
}
