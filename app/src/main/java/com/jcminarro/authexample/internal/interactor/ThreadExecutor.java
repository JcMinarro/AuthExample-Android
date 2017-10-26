package com.jcminarro.authexample.internal.interactor;

public interface ThreadExecutor {

    void execute(final Runnable runnable);
}