package com.android.play.uwfilm.data.movie

import com.android.play.uwfilm.data.movie.datasource.KobisMovieDataSource

interface MovieDataSource {
    suspend fun fetchNowPlayingList(date: String): List<Movie>
    suspend fun fetchMovieInformation(movieCode: String): String
    suspend fun fetchComingSoonList(): List<Movie>
}

data class Movie(val code: String,
                 val ranking: Int,
                 val title: String,
                 val genre: String = "코미디, 드라마",
                 val openDate: String = "2011-11-30",
                 val grade: String = "12세이상관람가",
                 var thumb: String = "https://www.kobis.or.kr/common/mast/movie/2022/11/thumb_x192/thn_ae69c79963c64a358013ccb42299a143.jpg")

class Movies {

    private val dataSource: MovieDataSource by lazy {
        KobisMovieDataSource()
    }

    suspend fun fetchNowPlayingList(): List<Movie> {
        return dataSource.fetchNowPlayingList("20221130")
    }

    suspend fun fetchMovieInformation(movieCode: String): String {
        return dataSource.fetchMovieInformation(movieCode)
    }

    suspend fun fetchComingSoonList(): List<Movie> = dataSource.fetchComingSoonList()

}