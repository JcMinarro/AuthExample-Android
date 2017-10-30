package com.jcminarro.authexample.internal.network.refresh

import com.jcminarro.authexample.internal.network.OAuth

fun map(refreshResponse: RefreshResponse): OAuth = OAuth(refreshResponse.accessToken, refreshResponse.refreshToken)