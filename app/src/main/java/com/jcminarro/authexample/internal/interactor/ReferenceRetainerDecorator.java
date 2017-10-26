package com.jcminarro.authexample.internal.interactor;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

/**
 * This class is in charge of holding references to all the interactor callbacks during the lifetime of its holder,
 * primarily, the presenter.
 * <p>
 * This is done in order to keep anonymous classes referenced either during the interactor execution lifetime or
 * the presenter lifetime, whichever lives less.
 */
public class ReferenceRetainerDecorator implements ExecutorCallbackDecorator {

    private final Set<InteractorExecutor.Callback<?, ?>> callbacks = new HashSet<>();

    @Inject
    public ReferenceRetainerDecorator() {
    }

    public <O, E> InteractorExecutor.Callback<O, E> decorate(InteractorExecutor.Callback<O, E> callback) {
        CallbackDecorator<O, E> decorator = new CallbackDecorator<>(
                callbacks,
                callback
        );
        callbacks.add(decorator);
        return decorator;
    }

    private static class CallbackDecorator<O, E> implements InteractorExecutor.Callback<O, E> {

        private final Set<InteractorExecutor.Callback<?, ?>> callbacks;
        private final InteractorExecutor.Callback<O, E> retainedCallback;

        private CallbackDecorator(
                Set<InteractorExecutor.Callback<?, ?>> callbacks,
                InteractorExecutor.Callback<O, E> retainedCallback) {
            this.callbacks = callbacks;
            this.retainedCallback = retainedCallback;
        }

        @Override
        public void onSuccess(O output) {
            retainedCallback.onSuccess(output);
            callbacks.remove(retainedCallback);
        }

        @Override
        public void onError(E exception) {
            retainedCallback.onError(exception);
            callbacks.remove(retainedCallback);
        }
    }
}
