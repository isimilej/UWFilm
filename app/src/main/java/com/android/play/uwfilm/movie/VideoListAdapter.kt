package com.android.play.uwfilm.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.play.uwfilm.data.movie.Trailer
import com.android.play.uwfilm.databinding.ItemVideoBinding
import com.bumptech.glide.Glide

class VideoListAdapter() : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {
    private var items = listOf<Trailer>()
    override fun getItemCount() = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemVideoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(trailerList: List<Trailer>) {
        items = trailerList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Trailer) {
            // video id
            // video title ???

            //https://i.ytimg.com/vi/NSs4xMpa1CA/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLCzQWfG6-webwiR2FeGftqufy4aSQ
            //https://www.youtube.com/watch?v=ICOyv2uuG_s
            //https://img.youtube.com/vi/MRc3o3ZEmHU/default.jpg - thumbnail
            //https://img.youtube.com/vi/MRc3o3ZEmHU/hqdefault.jpg - For the high quality version of the thumbnail
            //https://img.youtube.com/vi/MRc3o3ZEmHU/mqdefault.jpg - medium quality version
            //https://img.youtube.com/vi/MRc3o3ZEmHU/sddefault.jpg - standard definition version
            //https://img.youtube.com/vi/MRc3o3ZEmHU/maxresdefault.jpg - maximum resolution version
            //https://img.youtube.com/vi/MRc3o3ZEmHU/0.jpg -- full size image
            val thumbnail = "https://img.youtube.com/vi/${item.key}/hq720.jpg"
            Log.e("THUMB", thumbnail)
            Glide.with(binding.root).load(thumbnail).into(binding.videoThumbnail)
            binding.title.text = item.title
        }
    }
}