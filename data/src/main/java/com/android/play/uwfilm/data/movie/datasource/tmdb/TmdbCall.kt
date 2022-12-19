package com.android.play.uwfilm.data.movie.datasource.tmdb

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TmdbCall<T>(private val call: Call<T>) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                try {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback.onResponse(this@TmdbCall, Response.success(Result.success(body)))
                        } else {
                            callback.onResponse(
                                this@TmdbCall, Response.success(Result.failure(IllegalStateException("response body is empty(null).")))
                            )
                        }
                    } else {
                        val e = response.errorBody()?.let {
                            IllegalStateException(it.toString())
                        } ?: IllegalStateException("Unknown exception occurred")
                        callback.onResponse(this@TmdbCall, Response.success(Result.failure(e)))
                    }
                } catch (e: Exception) {
                    callback.onResponse(this@TmdbCall, Response.success(Result.failure(e)))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@TmdbCall, Response.success(Result.failure(t)))
            }
        })
    }

    override fun clone(): Call<Result<T>> = TmdbCall(call.clone())

    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException("TmdbCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

}