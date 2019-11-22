package com.jcminarro.authexample.internal.network

import arrow.core.Try
import retrofit2.Call
import java.io.IOException

open class ApiClient<T>(protected val endpoint: T) {

    @Throws(IOException::class)
    protected fun <U> evaluateCall(call: Call<U>): U {
        val response = call.execute()
        if (!response.isSuccessful) {
            throw APIIOException(response.raw())
        }
        return response.body()
    }

    protected fun <U> evaluateCallFP(call: Call<U>): Try<U> = Try {evaluateCall(call)}
}
