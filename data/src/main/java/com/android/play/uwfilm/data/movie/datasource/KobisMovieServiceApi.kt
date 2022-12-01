package com.android.play.uwfilm.data.movie.datasource

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisMovieServiceApi {
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    suspend fun fetch(@Query("targetDt") date: String = "20221128"): Response<String>

    @GET("/kobisopenapi/webservice/rest/movie/searchMovieInfo.json")
    suspend fun fetchMovieInformation(@Query("movieCd") movieCode: String): Response<String>
}