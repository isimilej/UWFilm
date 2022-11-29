package com.android.play.uwfilm.data.movie

import com.android.play.uwfilm.data.movie.datasource.KobisMovieDataSource

interface MovieDataSource {
    suspend fun fetchNowPlayingList(date: String): List<Movie>
}

data class Movie(val title: String)

class Movies {

    private val dataSource: MovieDataSource by lazy {
        KobisMovieDataSource()
    }

    suspend fun fetchNowPlayingList(): List<Movie> {
        return dataSource.fetchNowPlayingList("20220808")
    }
}