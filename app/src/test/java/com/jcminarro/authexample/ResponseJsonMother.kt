package com.jcminarro.authexample

import com.jcminarro.authexample.internal.network.login.LoginResponse
import com.jcminarro.authexample.internal.network.quote.QuoteResponse
import com.jcminarro.authexample.internal.network.refresh.RefreshResponse

fun createLoginResponseJson(loginResponse: LoginResponse): String =
        """
            {
            "accessToken": "${loginResponse.accessToken}",
            "refreshToken": "${loginResponse.refreshToken}"
            }
        """.trimIndent()

fun createRefreshResponseJson(refreshResponse: RefreshResponse): String =
        """
            {
            "accessToken": "${refreshResponse.accessToken}",
            "refreshToken": "${refreshResponse.refreshToken}"
            }
        """.trimIndent()

fun createQuoteResponseJson(quoteResponse: QuoteResponse): String =
        """
            {
            "author": "${quoteResponse.author}",
            "message": "${quoteResponse.message}"
            }
        """.trimIndent()