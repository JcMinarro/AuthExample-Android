package com.jcminarro.authexample.internal.network.login;

import com.google.gson.annotations.SerializedName;

public class LoginBody {
    private final @SerializedName("username") String username;
    private final @SerializedName("password") String password;

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
