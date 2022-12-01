package com.android.play.uwfilm.data.movie.datasource

import com.android.play.uwfilm.data.movie.Movie
import com.android.play.uwfilm.data.movie.MovieDataSource
import org.json.JSONObject

class KobisMovieDataSource : MovieDataSource {

//        "dailyBoxOfficeList": [
//        {
//            "rnum": "1", // 순번을 출력합니다.
//            "rank": "1", -- 순위 // 해당일자의 박스오피스 순위를 출력합니다.
//            "rankInten": "0", // 전일대비 순위의 증감분을 출력합니다.
//            "rankOldAndNew": "OLD", // 랭킹에 신규진입여부를 출력합니다.“OLD” : 기존 , “NEW” : 신규
//            "movieCd": "20112207", // 영화의 대표코드를 출력합니다.
//            "movieNm": "미션임파서블:고스트프로토콜", // 영화명(국문)을 출력합니다.
//            "openDt": "2011-12-15", // 영화의 개봉일을 출력합니다.
//            "salesAmt": "2776060500", // 해당일의 매출액을 출력합니다.
//            "salesShare": "36.3", // 해당일자 상영작의 매출총액 대비 해당 영화의 매출비율을 출력합니다.
//            "salesInten": "-415699000", // 전일 대비 매출액 증감분을 출력합니다.
//            "salesChange": "-13", // 전일 대비 매출액 증감 비율을 출력합니다.
//            "salesAcc": "40541108500", // 누적매출액을 출력합니다.
//            "audiCnt": "353274", // 해당일의 관객수를 출력합니다.
//            "audiInten": "-60106", // 전일 대비 관객수 증감분을 출력합니다.
//            "audiChange": "-14.5", // 전일 대비 관객수 증감 비율을 출력합니다.
//            "audiAcc": "5328435", // 누적관객수를 출력합니다.
//            "scrnCnt": "697", // 해당일자에 상영한 스크린수를 출력합니다.
//            "showCnt": "3223" // 해당일자에 상영된 횟수를 출력합니다.
//        } ]
    override suspend fun fetchNowPlayingList(date: String): List<Movie> {
        var movies = mutableListOf<Movie>()
        val response = KobisDataSourceProvider.provideApi<KobisMovieServiceApi>().fetch(date)
        val content = response.body()!!
        val json = JSONObject(content)
        var jsonarr = json.getJSONObject("boxOfficeResult").getJSONArray("dailyBoxOfficeList")
        for (i in 0 until jsonarr.length()) {
            var rank = (jsonarr[i] as JSONObject).getString("rank").toInt()
            var moveName = (jsonarr[i] as JSONObject)["movieNm"] as String
            var code = (jsonarr[i] as JSONObject)["movieCd"] as String
            movies.add(Movie(ranking = rank, code = code, title = moveName))
        }
        return movies
    }
}