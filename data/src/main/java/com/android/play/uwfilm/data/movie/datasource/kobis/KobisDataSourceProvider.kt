package com.android.play.uwfilm.data.movie.datasource.kobis

import com.android.play.uwfilm.data.Configuration
import com.android.play.uwfilm.data.net.ssl.HttpsTrustManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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
            .baseUrl(Configuration.KOBIS_API_BASE_URL)
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
                .addQueryParameter("key", Configuration.KOBIS_API_KEY)
                .build()

            if (originalHttpUrl.url().path.endsWith(".do"))
                chain.proceed(original)
            else
                chain.proceed(original.newBuilder().url(url).build())
        }

        val trustManagers = arrayOf<TrustManager>(HttpsTrustManager())
        val context: SSLContext = SSLContext.getInstance("TLS")
        context.init(null, trustManagers, SecureRandom())

        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(logging)
            .addInterceptor(apiKeyInterceptor)
            .sslSocketFactory(context.socketFactory, HttpsTrustManager())
            .build()
    }
}