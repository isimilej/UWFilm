package com.android.play.uwfilm.movie

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.android.play.uwfilm.R
import com.android.play.uwfilm.data.UWFilmApplication
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.data.movie.entity.Movie
import com.android.play.uwfilm.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.launch


class DetailFragment() : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var key: String? = null

    private var exoPlayer: ExoPlayer? = null

    override fun onStop() {
        super.onStop()
        exoPlayer?.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        arguments?.let {
            val movie = UWFilmApplication.getInstance().findKobisMovie(it.getString("code", ""))
            if (movie == null) {
                alert(getString(R.string.not_found_movie))
            } else {
                update(movie)
                updateVideo(movie)
                updateStillCut(movie)
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

            Movies().getTrailerVideoList(movie.tmdbMovieId).onSuccess {
                if (it.isNotEmpty()) {
                    initPlayer("https://www.youtube.com/watch?v=${it[0].key}")
                }
            }
        }

        //https://www.youtube.com/watch?v=ICOyv2uuG_s
        //https://img.youtube.com/vi/MRc3o3ZEmHU/default.jpg - thumbnail
        //https://img.youtube.com/vi/MRc3o3ZEmHU/hqdefault.jpg - For the high quality version of the thumbnail
        //https://img.youtube.com/vi/MRc3o3ZEmHU/mqdefault.jpg - medium quality version
        //https://img.youtube.com/vi/MRc3o3ZEmHU/sddefault.jpg - standard definition version
        //https://img.youtube.com/vi/MRc3o3ZEmHU/maxresdefault.jpg - maximum resolution version
        //https://img.youtube.com/vi/MRc3o3ZEmHU/0.jpg -- full size image
        //https://img.youtube.com/vi/ICOyv2uuG_s/0.jpg -- full size image

        val youtubeLink = "https://www.youtube.com/watch?v=sdFIv-yogIY"

        // get youtube video stream url
        object : YouTubeExtractor(requireContext()) {
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
                if (ytFiles != null) {
                    val itag = 22
                    val downloadUrl = ytFiles[itag].url
                    Log.e("TEST", "DownloadURL=$downloadUrl")
                    initPlayer(downloadUrl)
                }
            }
        }.extract(youtubeLink)
    }

    private fun initPlayer(videoUrl: String) {
        buildMediaSource(videoUrl)?.let {
            exoPlayer = ExoPlayer.Builder(requireContext()).build()
            exoPlayer?.setMediaSource(it)
            exoPlayer?.prepare()
            binding.player.player = exoPlayer
        }
    }

    private fun buildMediaSource(videoUrl: String): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), "simple")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))
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
                    Log.e("UPDATE--", it.url)
                    urlList.add(it.url)
                }
                (binding.stillCutRecycler.adapter as StillCutAdapter).update(urlList)
            }
        }
    }
}