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
import com.android.play.uwfilm.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class DetailFragment(val movieCode: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.movieCode.text = movieCode

        lifecycleScope.launch {
            var content = Movies().fetchMovieInformation(movieCode)
            Log.e("DETAIL", "CONTENT=$content")

            Glide.with(this@DetailFragment).load(content).into(binding.thumb)
        }
        return binding.root
    }
}