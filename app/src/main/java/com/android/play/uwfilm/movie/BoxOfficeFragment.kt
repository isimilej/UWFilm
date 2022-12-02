package com.android.play.uwfilm.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.play.uwfilm.DetailFragment
import com.android.play.uwfilm.R
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.databinding.FragmentBoxOfficeBinding
import kotlinx.coroutines.launch

class BoxOfficeFragment : Fragment() {

    private lateinit var binding: FragmentBoxOfficeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_box_office, container, false)

        var adapter = MovieAdapter(mutableListOf())
        adapter.setItemClickListener { movieCode ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailFragment(movieCode), null)
                .addToBackStack(null)
                .commit()
        }
        binding.boxOffices.adapter = adapter

        lifecycleScope.launch {
            try {
                var movies = Movies().fetchNowPlayingList()
                adapter.update(movies)

                for (movie in movies) {
                    var url = Movies().fetchMovieInformation(movie.code)
                    movie.thumb = url
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Log.e("Exception", "Exception==>$e")
            }
        }
        return binding.root
    }
}