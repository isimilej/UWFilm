package com.android.play.uwfilm.data.movie

import com.android.play.uwfilm.data.movie.datasource.kobis.KobisDataSource
import com.android.play.uwfilm.data.movie.datasource.tmdb.TmdbDataSource
import com.android.play.uwfilm.data.movie.entity.BoxOffice
import com.android.play.uwfilm.data.movie.entity.Movie
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface MovieDataSource {
    suspend fun fetchDailyBoxOfficeList(date: String): Result<List<BoxOffice>>
    suspend fun fetchMovieInformation(movieCode: String): Movie
    suspend fun fetchComingSoonList(): List<BoxOffice>
    suspend fun fetchDetail(movieCode: String): Unit
    suspend fun search(movieName: String): Result<Movie>
    suspend fun fetchVideos(movieCode: String): Result<Trailer>
}


class Movies {

    private val kobisDataSource: MovieDataSource by lazy {
        KobisDataSource()
    }

    private val tmdbDataSource: MovieDataSource by lazy {
        TmdbDataSource()
    }

    suspend fun fetchDailyBoxOfficeList(): Result<List<BoxOffice>> {
        val yesterday = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        return fetchDailyBoxOfficeList(yesterday)
    }

    suspend fun fetchDailyBoxOfficeList(date: String): Result<List<BoxOffice>> {
        return kobisDataSource.fetchDailyBoxOfficeList(date)
    }

    suspend fun fetchMovieInformation(movieCode: String): Movie {
        return kobisDataSource.fetchMovieInformation(movieCode)
    }

    suspend fun fetchDetail(movieCode: String): Unit {
        tmdbDataSource.fetchDetail(movieCode)
    }

    suspend fun search(movieName: String): Result<Movie> {
        return tmdbDataSource.search(movieName)
    }

    suspend fun fetchVideos(movieCode: String): Result<Trailer> {
        return tmdbDataSource.fetchVideos(movieCode)
    }

    suspend fun fetchComingSoonList(): List<BoxOffice> = kobisDataSource.fetchComingSoonList()

}