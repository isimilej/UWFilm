package com.android.play.uwfilm.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.play.uwfilm.data.movie.entity.BoxOffice
import com.android.play.uwfilm.databinding.ItemMovieBinding

class MovieAdapter(private var items: List<BoxOffice>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun interface ItemClickListener {
        fun onClick(movieCode: String)
    }
    private var itemClickListener: ItemClickListener? = null

    fun setItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(movieNames: List<BoxOffice>) {
        items = movieNames
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BoxOffice) {
            binding.boxoffice = item
//            Glide.with(binding.root).load(item.thumb).into(binding.thumb)
            binding.root.setOnClickListener {
                Log.e("", "Clicked!!")
                itemClickListener?.let {
                    it.onClick(item.code)
                }
                // change select item..
            }
        }
    }

    fun onClickItem(movie: BoxOffice) {

    }

}