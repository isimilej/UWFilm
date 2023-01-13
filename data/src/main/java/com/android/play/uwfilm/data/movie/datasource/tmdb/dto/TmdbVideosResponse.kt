package com.android.play.uwfilm.data.movie.datasource.tmdb.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbVideosResponse(
    @SerialName("results") val videoList: List<VideoResult> = listOf()
) {
    @Serializable
    data class VideoResult(
        val name: String = "",
        val key: String = "",
        val site: String = "",
        val type: String = "Trailer",
        val official: Boolean = false
    )
}