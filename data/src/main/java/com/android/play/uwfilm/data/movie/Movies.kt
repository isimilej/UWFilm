package com.android.play.uwfilm.data.movie

import com.android.play.uwfilm.data.movie.datasource.kobis.KobisDataSource
import com.android.play.uwfilm.data.movie.datasource.tmdb.TmdbDataSource
import com.android.play.uwfilm.data.movie.entity.BoxOffice
import com.android.play.uwfilm.data.movie.entity.Movie
import com.android.play.uwfilm.data.movie.entity.SearchResult
import com.android.play.uwfilm.data.movie.entity.StillCut
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface MovieDataSource {
    suspend fun fetchDailyBoxOfficeList(): Result<List<BoxOffice>>
    suspend fun fetchMovieInformation(movieCode: String): Result<List<StillCut>>
    suspend fun fetchComingSoonList(): List<BoxOffice>
    suspend fun fetchDetail(movieCode: String)
    suspend fun getTrailerVideos(movieCode: String): Result<List<Trailer>>
    suspend fun search(title: String): Result<List<SearchResult>>
}

class Movies {

    private val kobisDataSource: MovieDataSource by lazy {
        KobisDataSource()
    }

    private val tmdbDataSource: MovieDataSource by lazy {
        TmdbDataSource()
    }

    suspend fun fetchDailyBoxOfficeList(): Result<List<BoxOffice>> {
        return kobisDataSource.fetchDailyBoxOfficeList()
    }

    suspend fun searchFromTmdb(title: String): Result<List<SearchResult>> {
        val response = tmdbDataSource.search(title).onSuccess { return Result.success(it) }
        return Result.failure(response.exceptionOrNull() ?: IllegalStateException("Unknown Exception"))
    }

    suspend fun getTrailerVideoList(movieId: String): Result<List<Trailer>> {
        return tmdbDataSource.getTrailerVideos(movieId)
    }

    suspend fun getStillCutList(movieCode: String): Result<List<StillCut>> {
        return kobisDataSource.fetchMovieInformation(movieCode)
    }

}