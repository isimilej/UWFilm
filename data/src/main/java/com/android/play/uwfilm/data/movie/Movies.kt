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

    private val dataSource: MovieDataSource by lazy {
        KobisDataSource()
    }

    private val theMovieDbDataSource: MovieDataSource by lazy {
        TmdbDataSource()
    }

    suspend fun fetchDailyBoxOfficeList(date: String): Result<List<BoxOffice>> {
        return dataSource.fetchDailyBoxOfficeList(date)
    }

    suspend fun fetchDailyBoxOfficeList(): Result<List<BoxOffice>> {
        val yesterday = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        return dataSource.fetchDailyBoxOfficeList(yesterday)
    }

    suspend fun fetchMovieInformation(movieCode: String): Movie {
        return dataSource.fetchMovieInformation(movieCode)
    }

    suspend fun fetchDetail(movieCode: String): Unit {
        theMovieDbDataSource.fetchDetail(movieCode)
    }

    suspend fun search(movieName: String): Result<Movie> {
        return theMovieDbDataSource.search(movieName)
    }

    suspend fun fetchVideos(movieCode: String): Result<Trailer> {
        return theMovieDbDataSource.fetchVideos(movieCode)
    }

    suspend fun fetchComingSoonList(): List<BoxOffice> = dataSource.fetchComingSoonList()

}