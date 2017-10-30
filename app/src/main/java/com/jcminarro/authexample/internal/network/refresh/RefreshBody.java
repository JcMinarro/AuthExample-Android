package com.jcminarro.authexample.internal.network.refresh;

import com.google.gson.annotations.SerializedName;

public class RefreshBody {
    private final @SerializedName("refreshToken") String refreshToken;

    public RefreshBody(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
