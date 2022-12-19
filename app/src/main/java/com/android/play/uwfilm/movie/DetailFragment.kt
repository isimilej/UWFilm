package com.android.play.uwfilm.movie

import android.content.Intent
import android.net.Uri
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

    private var key: String? = null

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

            launch {
                Movies().search(movie.name).onSuccess { found ->
                    Movies().fetchVideos(found.code).onSuccess {
                        key = it.key
                    }
                }
            }

        }

        binding.btnPlay.setOnClickListener {
            key?.let {
//                "http://www.youtube.com/watch?v=" + it
//                Intent(Intent.ACTION_VIEW)
//
//                val intent = Intent(Intent.ACTION_VIEW).apply {
//                    addCategory(Intent.CATEGORY_BROWSABLE)
//                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
//                    data = Uri.parse(url)
//                }
//                context.startActivity(intent)
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse("https://play.google.com/store/apps/details?id=$YOUTUBE_PACKAGE_NAME")
//                startActivity(intent)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com/watch?v=$it")))

            }
        }
        return binding.root
    }
}