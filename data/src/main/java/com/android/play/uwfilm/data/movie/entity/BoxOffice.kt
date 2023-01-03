package com.android.play.uwfilm.data.movie.entity

data class BoxOffice(
    val movieCode: String, // 박스오피스 영화 코드
    val rank: Int = 0, // 박스오피스 랭킹
    val title: String = "", // 박스 오피스 제목
    val isNew: Boolean = false, // 랭킹 신규 진입(true)
    val rankIncrement: Int = 0, // 랭킹 증감분 "1","0","-2"
    val audienceCount: Int = 0, // 누적 관객수. "5328435"
    val openDate: String = "", // 개봉일
    val poster: String = "",
    val overview: String = "", // 영화 개요
    val genre: String = "", // 영화 장르
    val watchGrade: String = "", // 관람 등급
)