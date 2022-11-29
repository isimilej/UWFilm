package com.android.play.uwfilm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.databinding.FragmentMainBinding
import com.android.play.uwfilm.movie.MovieAdapter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        lifecycleScope.launch {
            try {
                // NOW PLAYING
                // OPENING THIS WEEK
                // COMING SOON
                var movies = mutableListOf<String>()

                var content = Movies().fetchNowPlayingList() // fetchComingSoonList, fetchOpeningThisWeek
                var json = JSONObject(content)
                var jsonarr = json.getJSONObject("boxOfficeResult").getJSONArray("dailyBoxOfficeList")
                for (i in 0 until jsonarr.length()) {
                    var moveName = (jsonarr[i] as JSONObject)["movieNm"] as String
                    movies.add(moveName)
                }
                binding.movies.adapter = MovieAdapter(movies)
            } catch (e: Exception) {
                Log.e("Exception", "Exception==>$e")
            }
        }
        return binding.root
    }
}