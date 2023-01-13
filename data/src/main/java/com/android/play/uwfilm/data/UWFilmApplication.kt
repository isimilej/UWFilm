package com.android.play.uwfilm.data

import android.app.Application
import com.android.play.uwfilm.data.movie.entity.BoxOffice
import com.android.play.uwfilm.data.movie.entity.Movie

class UWFilmApplication: Application() {

    var boxOfficeList: List<BoxOffice> = listOf()
    var movieList: List<Movie> = listOf()

    init {
        instance = this
    }

    companion object {
        private var instance: UWFilmApplication? = null
        fun getInstance(): UWFilmApplication {
            return instance as UWFilmApplication
        }
    }

    fun findKobisMovie(kobisMovieCode: String) = movieList.find { it.kobisMovieCode == kobisMovieCode }

}