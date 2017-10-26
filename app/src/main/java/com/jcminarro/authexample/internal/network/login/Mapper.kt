package com.jcminarro.authexample.internal.network.login

import com.jcminarro.authexample.internal.network.OAuth

fun map(loginResponse: LoginResponse): OAuth = OAuth(loginResponse.accessToken, loginResponse.refreshToken)