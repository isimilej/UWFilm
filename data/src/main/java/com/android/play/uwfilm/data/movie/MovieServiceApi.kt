package com.android.play.uwfilm.data.movie

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieServiceApi {
    @GET
    suspend fun fetch(@Url path: String): Response<String>
}