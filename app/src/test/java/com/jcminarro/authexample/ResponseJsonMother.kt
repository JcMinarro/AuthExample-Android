package com.jcminarro.authexample

import com.jcminarro.authexample.internal.network.login.LoginResponse

fun createLoginResponseJson(loginResponse: LoginResponse): String =
        """
            {
            "accessToken": "${loginResponse.accessToken}",
            "refreshToken": "${loginResponse.refreshToken}"
            }
        """.trimIndent()