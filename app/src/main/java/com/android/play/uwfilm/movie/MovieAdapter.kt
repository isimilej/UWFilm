package com.android.play.uwfilm.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.play.uwfilm.data.movie.entity.BoxOffice
import com.android.play.uwfilm.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

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
        fun bind(boxOffice: BoxOffice) {
            binding.boxoffice = boxOffice
            binding.ranking.text = boxOffice.rank.toString()
            //https://kobis.or.kr/common/mast/movie/2022/11/87e132235b634767b9c22e8483cfbba7.jpg
            //https://kobis.or.kr/common/mast/movie/2022/11/thumb_x289/thn_87e132235b634767b9c22e8483cfbba7.jpg
            Glide.with(binding.root).load(boxOffice.poster).into(binding.thumb)
            binding.grade.text = boxOffice.watchGrade
            binding.audience.text = DecimalFormat("#,###").format(boxOffice.audienceCount)
            binding.openDate.text = boxOffice.openDate
            binding.genre.text = boxOffice.genre

            binding.root.setOnClickListener {
                Log.e("", "Clicked!!")
                itemClickListener?.let {
                    it.onClick(boxOffice.movieCode)
                }
                // change select item..
            }
        }
    }

    fun onClickItem(movie: BoxOffice) {

    }

}