package com.android.play.uwfilm.data.movie.datasource.kobis

import com.android.play.uwfilm.data.movie.datasource.kobis.dto.DailyMainBoxOffice
import com.android.play.uwfilm.data.movie.datasource.kobis.dto.KobisResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisServiceApi {
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    suspend fun getDailyBoxOfficeList(@Query("targetDt") date: String): Result<KobisResponse>

    @GET("/kobis/business/main/searchMainDailyBoxOffice.do")
    suspend fun getDailyMainBoxOfficeList(): Result<List<DailyMainBoxOffice>>
    // fetchDailyBoxOfficeListOnKobisMainPage

    @GET("/kobisopenapi/webservice/rest/movie/searchMovieInfo.json")
    suspend fun fetchMovieInformation(@Query("movieCd") movieCode: String): Response<String>

}