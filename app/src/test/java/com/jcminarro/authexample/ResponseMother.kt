package com.jcminarro.authexample

import com.jcminarro.authexample.internal.network.login.LoginResponse

object ResponseMother{
    const val LOGIN_MOTHERR_accessToken = "accessToken"
    const val LOGIN_MOTHERR_refreshToken = "refreshToken"
}

fun createLoginResponse(accessToken: String = ResponseMother.LOGIN_MOTHERR_accessToken,
                        refreshToken: String = ResponseMother.LOGIN_MOTHERR_refreshToken) =
        LoginResponse(accessToken, refreshToken)