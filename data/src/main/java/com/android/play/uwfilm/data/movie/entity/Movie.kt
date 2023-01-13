package com.android.play.uwfilm.data.movie.entity

data class Movie(
    val kobisMovieCode: String = "",
    var tmdbMovieId: String = "",
    val title: String = "",
    val overview: String = "",
    val trailer: String = "",
    val poster: String = "",
    val openDate: String = "",
)