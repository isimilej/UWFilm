package com.android.play.uwfilm

import android.app.Application
import com.android.play.uwfilm.data.movie.entity.BoxOffice

class UWFilmApplication: Application() {

    var boxOfficeList: List<BoxOffice> = listOf()

    init {
        instance = this
    }

    companion object {
        private var instance: UWFilmApplication? = null
        fun getInstance(): UWFilmApplication {
            return instance as UWFilmApplication
        }
    }

}