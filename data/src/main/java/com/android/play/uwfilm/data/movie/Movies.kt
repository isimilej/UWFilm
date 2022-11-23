package com.android.play.uwfilm.data.movie

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Movies {
    // https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20221120
    fun fetchList(): MutableList<String> {
        var client = Retrofit.Builder()
            .baseUrl("https://kobis.or.kr/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        client.create(MovieServiceApi::class.java).fetch("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20221120").enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("onResponse", response.body()?: response.toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
                Log.e("onFailure", t.message ?: "")
            }

        })
        return mutableListOf("엔시티 드림 더 무비 : 인 어 드림", "올빼미", "동감", "블랙 팬서-와칸다 포에버", "유포자들")
    }
}