package com.android.play.uwfilm.data.movie.datasource.tmdb

import com.android.play.uwfilm.data.movie.datasource.tmdb.dto.TmdbVideosResponse
import com.android.play.uwfilm.data.movie.datasource.tmdb.dto.TmdbSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbServiceApi {
    @GET("/3/movie/{movieCode}?language=ko&region=kr")
    suspend fun fetchDetail(@Path("movieCode") movieCode: String): Unit

    @GET("/3/search/movie?language=ko&region=kr&page=1&include_adult=false")
    suspend fun search(@Query("query") movieName: String): Result<TmdbSearchResponse>

    @GET("/3/movie/{movieId}/videos?language=ko&region=kr&page=1&include_adult=false")
    suspend fun getVideos(@Path("movieId") movieId: String): Result<TmdbVideosResponse>

    @GET("/3/movie/{movieId}/images?language=ko&region=kr&page=1&include_adult=false")
    suspend fun images(@Path("movieId") movieId: String): Result<String>
}