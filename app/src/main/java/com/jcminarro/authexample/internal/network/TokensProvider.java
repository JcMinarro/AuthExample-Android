package com.jcminarro.authexample.internal.network;

public interface TokensProvider {

    String getAccessToken();

    String getRefreshToken();
}
