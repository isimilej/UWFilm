package com.android.play.uwfilm.data.movie.datasource.tmdb.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("status_code") val statusCode: String = "",
    @SerialName("status_message") val message: String = "",
    @SerialName("success") val isSuccess: Boolean = false,
    val results: List<SearchResult> = listOf()
) {
    @Serializable
    data class SearchResult(
        val id: String = "", // the movie id
        val title: String = "", // 박스오피스 조회 일자. "20221215~20221215"
    )
}