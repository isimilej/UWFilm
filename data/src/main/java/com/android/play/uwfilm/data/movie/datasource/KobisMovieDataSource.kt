package com.android.play.uwfilm.data.movie.datasource

import android.util.Log
import com.android.play.uwfilm.data.movie.BoxOffice
import com.android.play.uwfilm.data.movie.Movie
import com.android.play.uwfilm.data.movie.MovieDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup

class KobisMovieDataSource : MovieDataSource {

    private val service: KobisMovieServiceApi by lazy {
        KobisDataSourceProvider.provideApi()
    }

    override suspend fun fetchDailyBoxOfficeList(date: String): Result<List<BoxOffice>> {
        service.fetchDailyBoxOfficeList(date).onSuccess { response ->
            response.faultInfo?.let {
                return Result.failure(IllegalStateException("Fault info: ${it.errorCode}, ${it.message}"))
            }

            val boxOfficeList = arrayListOf<BoxOffice>()
            response.boxOfficeResult?.dailyBoxOfficeList?.forEach {
                boxOfficeList.add(BoxOffice(code = it.movieCd, ranking = it.rank, title = it.movieNm))
            }
            return Result.success(boxOfficeList)
        }.onFailure { e ->
            return Result.failure(e)
        }
        return Result.failure(IllegalStateException("Reached.. End of function"))
    }

    override suspend fun fetchMovieInformation(movieCode: String): Movie {

        var thumb = ""
        var synopsis = ""

        //Content-Type: application/x-www-form-urlencoded; charset=UTF-8
        // 전송할 폼 데이터
        // 전송할 폼 데이터
        withContext(Dispatchers.IO) {
            val data: MutableMap<String, String> = HashMap()
            data["code"] = movieCode
            var response =
                Jsoup.connect("https://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do")
                    .data(data)
                    .method(Connection.Method.POST).execute()

            //ovf info info1
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

            var imgs = document.select(".item_tab.basic").select(".info.info2")[1].select(".thumb_slide").select("img")//.attr("src")
            for (img in imgs) {
                Log.e("", "THUMB==${img.attr("src")}")
            }

            synopsis = document.select(".item_tab.basic").select(".info.info2").select(".desc_info")[0].text()
            //오직 목소리 하나로 전 세계를 사로잡은 디바 ‘우타’. 그녀가 모습을 드러내는 첫 라이브 콘서트가 음악의 섬 ‘엘레지아’에서 열리고 ‘루피’가 이끄는 밀짚모자 해적단과 함께 수많은 ‘우타’ 팬들로 공연장은 가득 찬다. 그리고 이 콘서트를 둘러싼 해적들과 해군들의 수상한 움직임이 시작되는데… 드디어 전세계가 애타게 기다리던 ‘우타’의 등장과 함께 노래가 울려 퍼지고, 그녀가 ‘샹크스의 딸’이라는 충격적인 사실이 드러난다. ‘루피’, ‘우타’, ‘샹크스’, 세 사람의 과거가 그녀의 노랫소리와 함께 밝혀진다!

            //Log.e("", "RESPONSE=https://www.kobis.or.kr${url}")
            Log.e("", "SYNOPSIS=${synopsis}")
            thumb = "https://www.kobis.or.kr${url}"

            //https://kobis.or.kr/kobis/web/comm/comm/photoDtl.html, POST,
            //https://kobis.or.kr/common/mast/movie/2022/12/thumb_x640/thn_c8d3fa881a854254842f9e63ca69b3e6.jpg
            // thumb_x132
        }

        return Movie(code = movieCode, thumb = thumb, synopsis = synopsis)
    }

    override suspend fun fetchComingSoonList(): List<BoxOffice> = mutableListOf()
}