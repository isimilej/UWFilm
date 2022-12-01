package com.android.play.uwfilm.data.movie.datasource

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisMovieServiceApi {
    //https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20221120
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    suspend fun fetch(@Query("targetDt") date: String = "20221128"): Response<String>
}