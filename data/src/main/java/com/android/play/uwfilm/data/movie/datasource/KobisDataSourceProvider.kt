package com.android.play.uwfilm.data.movie.datasource

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

const val KOBIS_API_KEY = "edccb79956300fa2e4465b09cf51fe25"

object KobisDataSourceProvider {

    inline fun <reified T> provideApi(): T {
        return retrofit.create(T::class.java)
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://kobis.or.kr/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private val okHttpClient: OkHttpClient by lazy {

        var logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        var apiKeyInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("key", KOBIS_API_KEY)
                .build()

            chain.proceed(original.newBuilder().url(url).build())
        }

        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(logging)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }
}