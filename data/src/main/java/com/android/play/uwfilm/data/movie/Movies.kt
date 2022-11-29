package com.android.play.uwfilm.data.movie

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class Movies {

    // https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20221120
    suspend fun fetchNowPlayingList(): String {

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

        var response = retrofit.create(MovieServiceApi::class.java)
            .fetch("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20221128")
        return response.body()!!
    }

}