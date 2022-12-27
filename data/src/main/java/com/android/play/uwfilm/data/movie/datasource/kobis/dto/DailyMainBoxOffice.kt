package com.android.play.uwfilm.data.movie.datasource.kobis.dto

import kotlinx.serialization.Serializable

@Serializable
data class DailyMainBoxOffice(
    val movieCd: String = "", // 영화 대표 코드
    val showDt: String = "",
    val thumbUrl: String = "",
    val movieNm: String = "", // 영화 제목
    val movieNmEn: String = "", // 영화 제목(영문)
    val synop: String = "", // 개요
    val showTm: Int = 0, // 상영시간(단위: 분)
    val genre: String = "", // 장르(예 액션,어드벤처,SF)
    val watchGradeNm: String = "", // 관람등급명칭(예 전체관람가, 12세이상관람가, 15세이상관람가, 18세이상관람가)
    val openDt: String = "", // 개봉일
    val rank: Int = 0, // 박스오피스 순위
    val rankInten: Int = 0, // 박스오피스 증감 순위
    val rankOldAndNew: String = "NEW", // 박스오피스 순위 신규 진입(NEW), 기존(OLD)
    val rownum: Int = 0, // 순번
)