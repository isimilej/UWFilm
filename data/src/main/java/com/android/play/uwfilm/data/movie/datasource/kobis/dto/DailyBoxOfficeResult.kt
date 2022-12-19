package com.android.play.uwfilm.data.movie.datasource.kobis.dto

import kotlinx.serialization.Serializable

@Serializable
data class DailyBoxOfficeResult (
    val boxOfficeResult: BoxOfficeResult? = null, // 박스오피스 결과
    val faultInfo: FaultInfo? = null // 오류 - 오류가 발생할 경우에만 값이 있음, 정상적인 경우에는 null
) {
    @Serializable
    data class BoxOfficeResult(
        val boxofficeType: String = "일별 박스오피스", // 박스오피스 종류. "일별 박스오피스"
        val showRange: String = "", // 박스오피스 조회 일자. "20221215~20221215"
        val dailyBoxOfficeList: List<DailyBoxOffice> = listOf()
    )

    @Serializable
    data class DailyBoxOffice(
        val rnum: String = "", // 순번. "1"
        val rank: String = "", // 순위. "1"
        val rankInten: String = "", // 전일 대비 순위 증감분. "0" or "-1" or "2"
        val rankOldAndNew: String = "NEW", // 랭킹 신규 진입여부. "OLD": 기존, "NEW": 신규
        val movieCd: String = "", // 영화 대표 코드. "20112207"
        val movieNm: String = "", // 영화명(국문). "미션임파서블:고스트프로토콜"
        val openDt: String = "", // 영화 개봉일. "2011-12-15"
        val salesAmt: String = "", // 해당일의 매출액. "2776060500"
        val salesShare: String = "", // 해당일자 상영작의 매출총액 대비 해당 영화의 매출비율. "36.3"
        val salesInten: String = "", // 전일 대비 매출액 증감분. "-415699000"
        val salesChange: String = "", // 전일 대비 매출액 증감 비율. "-13"
        val salesAcc: String = "", // 누적 매출액. "40541108500"
        val audiCnt: String = "", // 해당일의 관객수. "353274"
        val audiInten: String = "", // 전일 대비 관객수 증감분. "-60106"
        val audiChange: String = "", // 전일 대비 관객수 증감 비율. "-14.5"
        val audiAcc: String = "", // 누적 관객수. "5328435"
        val scrnCnt: String = "", // 해당일자에 상영한 스크린 수. "697"
        val showCnt: String = "", // 해당 일자에 사영된 횟수. "3223"
    )
}