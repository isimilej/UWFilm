package com.android.play.uwfilm.data.movie

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class Movies {

    interface FetchCallback {
        fun onResponse(response: String)
        fun onFailure(error: Exception)
    }

    // https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20221120
    fun fetchList(callback: FetchCallback): MutableList<String> {

        var logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(logging)
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl("https://kobis.or.kr/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(MovieServiceApi::class.java).fetch("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20221120").enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    callback.onResponse(it)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callback.onFailure(t as Exception)
            }

        })
        return mutableListOf("엔시티 드림 더 무비 : 인 어 드림", "올빼미", "동감", "블랙 팬서-와칸다 포에버", "유포자들")
    }
}