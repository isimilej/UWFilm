package com.android.play.uwfilm.data.movie

import com.android.play.uwfilm.data.movie.datasource.kobis.KobisMovieDataSource
import com.android.play.uwfilm.data.movie.datasource.tmdb.TmdbDataSource

interface MovieDataSource {
    suspend fun fetchDailyBoxOfficeList(date: String): Result<List<BoxOffice>>
    suspend fun fetchMovieInformation(movieCode: String): Movie
    suspend fun fetchComingSoonList(): List<BoxOffice>
    suspend fun fetchDetail(movieCode: String): Unit
    suspend fun search(movieName: String): Result<Movie>
    suspend fun fetchVideos(movieCode: String): Result<Trailer>
}

data class BoxOffice(val code: String,
                     val ranking: String,
                     val title: String,
                     val genre: String = "코미디, 드라마",
                     val openDate: String = "2011-11-30",
                     val grade: String = "12세이상관람가",
                     var thumb: String = "https://www.kobis.or.kr/common/mast/movie/2022/11/thumb_x192/thn_ae69c79963c64a358013ccb42299a143.jpg")

data class Movie(val code: String, val name: String, val thumb: String, val synopsis: String)

class Movies {

    private val dataSource: MovieDataSource by lazy {
        KobisMovieDataSource()
    }

    private val theMovieDbDataSource: MovieDataSource by lazy {
        TmdbDataSource()
    }

    suspend fun fetchDailyBoxOfficeList(date: String): Result<List<BoxOffice>> {
        return dataSource.fetchDailyBoxOfficeList(date)
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