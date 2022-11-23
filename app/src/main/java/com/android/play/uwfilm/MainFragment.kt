package com.android.play.uwfilm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.databinding.FragmentMainBinding
import com.android.play.uwfilm.movie.MovieAdapter

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.movies.adapter = MovieAdapter(Movies().fetchList())
        return binding.root
    }
}