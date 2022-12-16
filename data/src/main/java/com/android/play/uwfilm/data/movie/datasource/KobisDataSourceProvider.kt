package com.android.play.uwfilm.data.movie.datasource

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

const val KOBIS_API_KEY = "edccb79956300fa2e4465b09cf51fe25"

object KobisDataSourceProvider {

    private val customJson = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true // data class 기본값 적용
        isLenient = true
        coerceInputValues = true
    }

    inline fun <reified T> provideApi(): T {
        return retrofit.create(T::class.java)
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://kobis.or.kr/")
            .addCallAdapterFactory(KobisCallAdapter.Factory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(Json {
                isLenient = true // Json 큰따옴표 느슨하게 체크.
                ignoreUnknownKeys = true // Field 값이 없는 경우 무시
                coerceInputValues = true // "null" 이 들어간경우 default Argument 값으로 대체
            }.asConverterFactory(MediaType.get("application/json")))
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