package com.jcminarro.authexample

import com.jcminarro.authexample.ResponseMother.QUOTE_MOTHERR_author
import com.jcminarro.authexample.ResponseMother.QUOTE_MOTHERR_message
import com.jcminarro.authexample.internal.network.login.LoginResponse
import com.jcminarro.authexample.internal.network.quote.QuoteResponse
import com.jcminarro.authexample.internal.network.refresh.RefreshResponse

object ResponseMother{
    const val LOGIN_MOTHERR_accessToken = "accessToken"
    const val LOGIN_MOTHERR_refreshToken = "refreshToken"
    const val REFRESH_MOTHERR_accessToken = "accessToken"
    const val REFRESH_MOTHERR_refreshToken = "refreshToken"
    const val QUOTE_MOTHERR_author = "author"
    const val QUOTE_MOTHERR_message = "message"
}

fun createLoginResponse(accessToken: String = ResponseMother.LOGIN_MOTHERR_accessToken,
                        refreshToken: String = ResponseMother.LOGIN_MOTHERR_refreshToken) =
        LoginResponse(accessToken, refreshToken)

fun createRefreshResponse(accessToken: String = ResponseMother.REFRESH_MOTHERR_accessToken,
                        refreshToken: String = ResponseMother.REFRESH_MOTHERR_refreshToken) =
        RefreshResponse(accessToken, refreshToken)

fun createQuoteResponse(author: String = QUOTE_MOTHERR_author,
                        message: String = QUOTE_MOTHERR_message) =
        QuoteResponse(author, message)