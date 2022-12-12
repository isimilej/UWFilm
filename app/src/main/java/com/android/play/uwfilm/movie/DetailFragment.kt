package com.android.play.uwfilm.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.play.uwfilm.R
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class DetailFragment(val movieCode: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_detail, container, false)

        lifecycleScope.launch {
            var movie = Movies().fetchMovieInformation(movieCode)
            Glide.with(this@DetailFragment).load(movie.thumb).into(binding.thumb)
            binding.synopsis.text = movie.synopsis
        }
        return binding.root
    }
}