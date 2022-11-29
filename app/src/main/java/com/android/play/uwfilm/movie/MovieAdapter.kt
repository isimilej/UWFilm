package com.android.play.uwfilm.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.play.uwfilm.data.movie.Movie
import com.android.play.uwfilm.databinding.ItemMovieBinding

class MovieAdapter(private var items: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(movieNames: MutableList<Movie>) {
        items = movieNames
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.title.text = item.title
        }
    }

}