package com.android.play.uwfilm.data.movie.datasource

import com.android.play.uwfilm.data.movie.Movie
import com.android.play.uwfilm.data.movie.MovieDataSource
import org.json.JSONObject

class KobisMovieDataSource : MovieDataSource {
    override suspend fun fetchNowPlayingList(date: String): List<Movie> {
        var movies = mutableListOf<Movie>()
        val response = KobisDataSourceProvider.provideApi<KobisMovieServiceApi>().fetch(date)
        val content = response.body()!!
        val json = JSONObject(content)
        var jsonarr = json.getJSONObject("boxOfficeResult").getJSONArray("dailyBoxOfficeList")
        for (i in 0 until jsonarr.length()) {
            var moveName = (jsonarr[i] as JSONObject)["movieNm"] as String
            movies.add(Movie(title = moveName))
        }
        return movies
    }
}