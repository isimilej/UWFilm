package com.android.play.uwfilm.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.play.uwfilm.R
import com.android.play.uwfilm.databinding.ItemStillCutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class StillCutAdapter(val context: Context) : RecyclerView.Adapter<StillCutAdapter.ViewHolder>() {

    private var stillCutList = listOf<String>()

    private val shimmer: Shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(1800)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    private val shimmerDrawable: ShimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    private val requestOptions = RequestOptions().apply {
        placeholder(shimmerDrawable)
        error(shimmerDrawable)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StillCutAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemStillCutBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stillCutList[position])
    }

    override fun getItemCount() = stillCutList.size

    fun update(stillCutList: List<String>) {
        this.stillCutList = stillCutList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemStillCutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(binding.stillCut)
        }
    }
}