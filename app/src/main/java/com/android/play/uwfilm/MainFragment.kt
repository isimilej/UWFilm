package com.android.play.uwfilm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.data.movie.Movies.FetchCallback
import com.android.play.uwfilm.databinding.FragmentMainBinding
import com.android.play.uwfilm.movie.MovieAdapter
import org.json.JSONArray
import org.json.JSONObject

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.movies.adapter = MovieAdapter(Movies().fetchList(object : FetchCallback {
            override fun onResponse(response: String) {
                Log.e("", ">>>>>>>$response");
                var json = JSONObject(response)
                var movies: JSONArray = (json["boxOfficeResult"] as JSONObject).getJSONArray("dailyBoxOfficeList")//[0] as JSONObject

                var movieNames = mutableListOf<String>()

//                for (i in 0 until movies.length()) {
//                    (movies[i] as JSONObject)["movieNm"]
//                }

                (0 until movies.length()).forEach { index ->
                    movieNames.add((movies[index] as JSONObject).optString("movieNm"))
                }

                (binding.movies.adapter as MovieAdapter).update(movieNames)
            }

            override fun onFailure(error: Exception) {
                Log.e("", ">>>>>>>$error");
            }
        }))
        return binding.root
    }
}