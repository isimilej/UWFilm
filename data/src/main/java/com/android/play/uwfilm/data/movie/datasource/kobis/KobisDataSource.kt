package com.android.play.uwfilm.data.movie.datasource.kobis

import android.util.Log
import com.android.play.uwfilm.data.movie.MovieDataSource
import com.android.play.uwfilm.data.movie.Trailer
import com.android.play.uwfilm.data.movie.datasource.kobis.dto.DailyBoxOffice
import com.android.play.uwfilm.data.movie.datasource.kobis.dto.DailyMainBoxOffice
import com.android.play.uwfilm.data.movie.entity.BoxOffice
import com.android.play.uwfilm.data.movie.entity.Movie
import com.android.play.uwfilm.data.movie.entity.SearchResult
import com.android.play.uwfilm.data.movie.entity.StillCut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup


class KobisDataSource : MovieDataSource {

    private val service: KobisServiceApi by lazy {
        KobisDataSourceProvider.provideApi()
    }

    override suspend fun fetchDailyBoxOfficeList(): Result<List<BoxOffice>> {
        val mainBoxOfficeListResult = service.getDailyMainBoxOfficeList().onSuccess { dailyMainBoxOfficeList ->
            service.getDailyBoxOfficeList(dailyMainBoxOfficeList[0].showDt).onSuccess { response ->
                val boxOfficeList = joinToBoxOfficeResult(dailyMainBoxOfficeList, response.boxOfficeResult.dailyBoxOfficeList)
                return Result.success(boxOfficeList) // return fetchDailyBoxOfficeList function
            }.onFailure { e ->
                return Result.failure(e)
            }
        }
        return Result.failure(mainBoxOfficeListResult.exceptionOrNull() ?: IllegalStateException("UnExpected Exception"))
    }

    private fun joinToBoxOfficeResult(dailyMainBoxOfficeList: List<DailyMainBoxOffice>, dailyBoxOfficeList: List<DailyBoxOffice>): List<BoxOffice> {
        val resultList = mutableListOf<BoxOffice>()
        dailyMainBoxOfficeList.forEach { mainBoxOffice ->
            dailyBoxOfficeList.find {
                it.movieCd == mainBoxOffice.movieCd
            }?.let { dailyBoxOffice ->
                resultList.add(BoxOffice(
                    kobisMovieCode = dailyBoxOffice.movieCd,
                    rank = dailyBoxOffice.rank,
                    title = dailyBoxOffice.movieNm, // 박스 오피스 제목
                    isNew = "NEW" == dailyBoxOffice.rankOldAndNew,
                    audienceCount = dailyBoxOffice.audiAcc,
                    openDate = dailyBoxOffice.openDt,
                    watchGrade = mainBoxOffice.watchGradeNm,
                    genre = mainBoxOffice.genre,
                    overview =  mainBoxOffice.synop,
                    poster = getPath(mainBoxOffice.thumbUrl, 289)))
            }
        }
        return resultList
    }

    private fun getPath(url: String, size: Int): String {
        return "https://kobis.or.kr" + url.replace("thumb/thn_", "thumb_x640/thn_")
    }

    override suspend fun fetchMovieInformation(movieCode: String): Result<List<StillCut>> {

        val stillCutList = mutableListOf<StillCut>()

        withContext(Dispatchers.IO) {
            val data: MutableMap<String, String> = HashMap()
            data["code"] = movieCode
            var response =
                Jsoup.connect("https://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do")
                    .data(data)
                    .method(Connection.Method.POST).execute()

            var document = response.parse()
            var url = document.select(".ovf.info.info1").select("img").attr("src")

            var content = document.select(".item_tab.basic").select(".ovf.info.info1").select(".ovf.cont").select("dd")
            Log.e("", "코드=${content[0].text()}")
            Log.e("", "ISAN=${content[1].text()}")
            Log.e("", "A.K.A=${content[2].text()}")
            Log.e("", "요약정보=${content[3].text()}")
            Log.e("", "개봉일=${content[4].text()}")
            Log.e("", "제작연도=${content[5].text()}")
            Log.e("", "제작상태=${content[6].text()}")

            var posters = document.select(".item_tab.basic").select(".info.info2")[0].select(".thumb_slide").select("img")
            for (poster in posters) {
                stillCutList.add(StillCut(url = "https://www.kobis.or.kr${poster.attr("src").replace("thumb_x110", "thumb_x640")}"))
            }

            // get steal thumbnail.
            var imgs = document.select(".item_tab.basic").select(".info.info2")[1].select(".thumb_slide").select("img")//.attr("src")
            for (img in imgs) {
                stillCutList.add(StillCut(url = "https://www.kobis.or.kr${img.attr("src").replace("thumb_x132", "thumb_x640")}"))
            }
        }
        return Result.success(stillCutList)
    }

    override suspend fun fetchComingSoonList(): List<BoxOffice> = mutableListOf()

    override suspend fun fetchDetail(movieCode: String) {
        TODO("Not yet implemented")
    }

    override suspend fun search(movieName: String): Result<List<SearchResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrailerVideos(movieCode: String): Result<List<Trailer>> {
        TODO("Not yet implemented")
    }
}