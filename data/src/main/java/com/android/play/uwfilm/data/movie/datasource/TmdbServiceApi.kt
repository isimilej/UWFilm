package com.android.play.uwfilm.data.movie.datasource

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbServiceApi {
    @GET("/3/movie/{movieCode}?language=ko&region=kr")
    suspend fun fetchDetail(@Path("movieCode") movieCode: String): Unit

    @GET("/3/search/movie?language=ko&region=kr&page=1&include_adult=false")
    suspend fun search(@Query("query") movieName: String)
}