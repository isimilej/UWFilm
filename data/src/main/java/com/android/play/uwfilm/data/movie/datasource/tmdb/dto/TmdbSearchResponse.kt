package com.android.play.uwfilm.data.movie.datasource.tmdb.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbSearchResponse(
    @SerialName("status_code") val statusCode: String = "",
    @SerialName("status_message") val message: String = "",
    @SerialName("success") val isSuccess: Boolean = false,
    val page: Int = 1,
    @SerialName("results") val resultList: List<SearchResult> = listOf()
) {
    @Serializable
    data class SearchResult(
        val id: String = "", // the tmdb movie id
        val title: String = "",
        @SerialName("release_date")  val releaseDate: String = "",
    )
}

//{
//    "page": 1,
//    "results": [
//    {
//        "adult": false,
//        "backdrop_path": "/zjpYDQlhvrAaohKRShu522sKJ87.jpg",
//        "genre_ids": [
//        16,
//        35,
//        18
//        ],
//        "id": 783675,
//        "original_language": "ja",
//        "original_title": "THE FIRST SLAM DUNK",
//        "overview": "전국 제패를 꿈꾸는 북산고 농구부 5인방의 꿈과 열정, 멈추지 않는 도전을 그린 영화",
//        "popularity": 67.532,
//        "poster_path": "/coiJrdXAXuBkSGDvp9bZ7mkuU6E.jpg",
//        "release_date": "2023-01-04",
//        "title": "더 퍼스트 슬램덩크",
//        "video": false,
//        "vote_average": 7.5,
//        "vote_count": 4
//    }
//    ],
//    "total_pages": 1,
//    "total_results": 1
//}