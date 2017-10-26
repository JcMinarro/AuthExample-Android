package com.jcminarro.authexample.internal.interactor;

import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class InUiThreadDispatcherDecorator implements ExecutorCallbackDecorator {

    private final Handler handler;

    @Inject
    public InUiThreadDispatcherDecorator() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public <O, E> InteractorExecutor.Callback<O, E> decorate(InteractorExecutor.Callback<O, E> callback) {
        return new CallbackDecorator<>(handler, callback);
    }

    private static class CallbackDecorator<O, E> implements InteractorExecutor.Callback<O, E> {

        private final Handler handler;
        private final WeakReference<InteractorExecutor.Callback<O, E>> callback;

        private CallbackDecorator(Handler handler, InteractorExecutor.Callback<O, E> callback) {
            this.handler = handler;
            this.callback = new WeakReference<>(callback);
        }

        @Override
        public void onSuccess(final O output) {
            final InteractorExecutor.Callback<O, E> unwrappedCallback = callback.get();
            if (unwrappedCallback != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        unwrappedCallback.onSuccess(output);
                    }
                });
            }
        }

        @Override
        public void onError(final E error) {
            final InteractorExecutor.Callback<O, E> unwrappedCallback = callback.get();
            if (unwrappedCallback != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        unwrappedCallback.onError(error);
                    }
                });
            }
        }
    }
}
