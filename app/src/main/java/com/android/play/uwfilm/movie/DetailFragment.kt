package com.android.play.uwfilm.movie

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.android.play.uwfilm.R
import com.android.play.uwfilm.data.UWFilmApplication
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.data.movie.Trailer
import com.android.play.uwfilm.data.movie.entity.Movie
import com.android.play.uwfilm.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

const val KEY_KOBIS_MOVIE_CODE = "KOBIS_MOVIE_CODE"
const val KEY_MOVIE_TITLE = "TITLE"

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var trailerList = listOf<Trailer>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        arguments?.let { bundle ->
            val kobisMovieCode = bundle.getString(KEY_KOBIS_MOVIE_CODE, "")
            val movie = UWFilmApplication.getInstance().movieList.find { it.kobisMovieCode == kobisMovieCode }
            if (movie == null) {
                alert(getString(R.string.not_found_movie))
            } else {
                update(movie)
                updateVideo(movie)
                updateStillCut(movie)
            }
        }

        binding.trailerPreview.setOnClickListener {
            if (trailerList.isNotEmpty()) {
                setFragmentResult("requestKey", bundleOf("bundleKey" to trailerList))
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, TrailerFragment(), null)
                    .addToBackStack(null)
                    .commit()
            }
        }
        return binding.root
    }

    private fun update(movie: Movie) {
        binding.title.text = movie.title
        Glide.with(binding.root).load(movie.poster).into(binding.poster)
        binding.overview.text = movie.overview
    }

    private fun updateVideo(movie: Movie) {
        lifecycleScope.launch {
            Movies().searchFromTmdb(movie.title).onSuccess { resultList ->
                resultList.forEach { result ->
                    if (result.openDate == movie.openDate) {
                        movie.tmdbMovieId = result.id
                    }
                }
            }

            Movies().getTrailerVideoList(movie.tmdbMovieId).onSuccess { trailerList ->
                if (trailerList.isNotEmpty()) {
                    this@DetailFragment.trailerList = trailerList
                    //https://www.youtube.com/watch?v=ICOyv2uuG_s
                    //https://img.youtube.com/vi/MRc3o3ZEmHU/default.jpg - thumbnail
                    //https://img.youtube.com/vi/MRc3o3ZEmHU/hqdefault.jpg - For the high quality version of the thumbnail
                    //https://img.youtube.com/vi/MRc3o3ZEmHU/mqdefault.jpg - medium quality version
                    //https://img.youtube.com/vi/MRc3o3ZEmHU/sddefault.jpg - standard definition version
                    //https://img.youtube.com/vi/MRc3o3ZEmHU/maxresdefault.jpg - maximum resolution version
                    //https://img.youtube.com/vi/MRc3o3ZEmHU/0.jpg -- full size image
                    val thumbnail = "https://img.youtube.com/vi/${trailerList[0].key}/hq720.jpg"
                    Glide.with(binding.root).load(thumbnail).into(binding.trailerPreview)
                }
            }
        }
    }

    private fun alert(message: String) {
        with (AlertDialog.Builder(requireContext())) {
            setMessage(message)
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun updateStillCut(movie: Movie) {
        val adapter = StillCutAdapter(requireContext())
        binding.stillCutRecycler.adapter = adapter

        lifecycleScope.launch {
            Movies().getStillCutList(movie.kobisMovieCode).onSuccess { stillCutList ->
                val urlList = mutableListOf<String>()
                stillCutList.forEach {
                    urlList.add(it.url)
                }
                (binding.stillCutRecycler.adapter as StillCutAdapter).update(urlList)
            }
        }
    }
}