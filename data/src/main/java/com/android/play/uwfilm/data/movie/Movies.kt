package com.android.play.uwfilm.data.movie

import com.android.play.uwfilm.data.movie.datasource.KobisMovieDataSource

interface MovieDataSource {
    suspend fun fetchDailyBoxOfficeList(date: String): ArrayList<BoxOffice>
    suspend fun fetchMovieInformation(movieCode: String): Movie
    suspend fun fetchComingSoonList(): List<BoxOffice>
}

data class BoxOffice(val code: String,
                     val ranking: Int,
                     val title: String,
                     val genre: String = "코미디, 드라마",
                     val openDate: String = "2011-11-30",
                     val grade: String = "12세이상관람가",
                     var thumb: String = "https://www.kobis.or.kr/common/mast/movie/2022/11/thumb_x192/thn_ae69c79963c64a358013ccb42299a143.jpg")

data class Movie(val code: String, val thumb: String, val synopsis: String)

class Movies {

    private val dataSource: MovieDataSource by lazy {
        KobisMovieDataSource()
    }

    suspend fun fetchDailyBoxOfficeList(date: String): ArrayList<BoxOffice> {
        return try {
            dataSource.fetchDailyBoxOfficeList(date)
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    suspend fun fetchMovieInformation(movieCode: String): Movie {
        return dataSource.fetchMovieInformation(movieCode)
    }

    suspend fun fetchComingSoonList(): List<BoxOffice> = dataSource.fetchComingSoonList()

}