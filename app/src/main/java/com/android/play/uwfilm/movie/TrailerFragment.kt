package com.android.play.uwfilm.movie

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.android.play.uwfilm.R
import com.android.play.uwfilm.data.movie.Trailer
import com.android.play.uwfilm.databinding.FragmentTrailerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

const val KEY_YOUTUBE_VIDEO = "YOUTUBE_VIDEO_KEY";

class TrailerFragment : Fragment() {

    private lateinit var binding: FragmentTrailerBinding
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
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trailer, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        setFragmentResultListener("requestKey") { key, bundle ->
            var trailerList: List<Trailer> = bundle.get("bundleKey") as List<Trailer>
            if (trailerList.isNotEmpty()) {
                updateVideo(trailerList[0])
                initVideoListAdapter(trailerList)
            }
        }
        return binding.root
    }

    private fun updateVideo(trailer: Trailer) {
        val youtubeLink = "https://www.youtube.com/watch?v=${trailer.key}"
        Log.e("YoutubeLienk", youtubeLink)
        object : YouTubeExtractor(requireContext()) {
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
                if (ytFiles != null) {
                    val itag = 22
                    initPlayer(ytFiles.get(itag).url)
                    Log.e("YoutubeLienk", ytFiles[22].url)
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

    private fun initVideoListAdapter(trailerList: List<Trailer>) {
        val adapter = VideoListAdapter()
        binding.videoRecycler.adapter = adapter
        adapter.update(trailerList)
    }

}