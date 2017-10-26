package com.jcminarro.authexample.internal.presenter.lifecycle;

class PresenterNotAccessibleException extends RuntimeException {

    PresenterNotAccessibleException(String detailMessage) {
        super(detailMessage);
    }
}