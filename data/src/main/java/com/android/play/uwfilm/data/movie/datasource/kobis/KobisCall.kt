package com.android.play.uwfilm.data.movie.datasource.kobis

import com.android.play.uwfilm.data.movie.datasource.kobis.dto.KobisResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KobisCall<T>(private val call: Call<T>) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                try {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            val faultInfo = (body as? KobisResponse)?.faultInfo
                            if (faultInfo != null) {
                                callback.onResponse(
                                    this@KobisCall,
                                    Response.success(
                                        Result.failure(IllegalStateException("Fault info: ${faultInfo.errorCode}, ${faultInfo.message}"))
                                    )
                                )
                            } else {
                                callback.onResponse(this@KobisCall, Response.success(Result.success(body)))
                            }
                        } else {
                            callback.onResponse(
                                this@KobisCall, Response.success(Result.failure(IllegalStateException("response body is empty(null).")))
                            )
                        }
                    } else {
                        val e = response.errorBody()?.let {
                            IllegalStateException(it.toString())
                        } ?: IllegalStateException("Unknown exception occurred")
                        callback.onResponse(this@KobisCall, Response.success(Result.failure(e)))
                    }
                } catch (e: Exception) {
                    callback.onResponse(this@KobisCall, Response.success(Result.failure(e)))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@KobisCall, Response.success(Result.failure(t)))
            }
        })
    }

    override fun clone(): Call<Result<T>> = KobisCall(call.clone())

    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException("KobisCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

}