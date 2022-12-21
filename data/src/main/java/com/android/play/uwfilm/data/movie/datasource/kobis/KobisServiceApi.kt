package com.android.play.uwfilm.data.movie.datasource.kobis

import com.android.play.uwfilm.data.movie.datasource.kobis.dto.DailyBoxOfficeResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisServiceApi {
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    suspend fun fetchDailyBoxOfficeList(@Query("targetDt") date: String): Result<DailyBoxOfficeResult>

    @GET("/kobisopenapi/webservice/rest/movie/searchMovieInfo.json")
    suspend fun fetchMovieInformation(@Query("movieCd") movieCode: String): Response<String>
}