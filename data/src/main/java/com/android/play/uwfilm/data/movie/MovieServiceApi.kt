package com.android.play.uwfilm.data.movie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieServiceApi {
    @GET
    fun fetch(@Url path: String): Call<String>
}