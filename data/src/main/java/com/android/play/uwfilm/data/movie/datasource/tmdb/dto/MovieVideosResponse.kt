package com.android.play.uwfilm.data.movie.datasource.tmdb.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieVideosResponse(
    val results: List<VideoResult> = listOf()
) {
    @Serializable
    data class VideoResult(
        val key: String = "",
        val type: String = "Trailer",
        val official: Boolean = false
    )
}