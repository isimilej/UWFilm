package com.android.play.uwfilm.movie

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
import com.android.play.uwfilm.databinding.FragmentBoxOfficeBinding
import com.android.play.uwfilm.widget.toast
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BoxOfficeFragment : Fragment() {

    private lateinit var binding: FragmentBoxOfficeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_box_office, container, false)

        var adapter = MovieAdapter(mutableListOf())
        adapter.setItemClickListener { movieCode ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailFragment(movieCode), null)
                .addToBackStack(null)
                .commit()
        }
        binding.boxOffices.adapter = adapter


        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
        }

        lifecycleScope.launch {
            try {
                // FIXME: 오전, 몇시 부터 전날 데이터를 이용할 수 있는가???
                val date = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))

                var result = Movies().fetchDailyBoxOfficeList(date)
                    .onSuccess { boxOfficeList ->
                        adapter.update(boxOfficeList)
                    }
                    .onFailure {
                        toast("박스오피스 정보를 가져오지 못했습니다.")
                    }

                Log.e("BoxOfficeFragment", "$result")

//
//
//                for (boxOffice in boxOfficeList) {
//                    var movie = Movies().fetchMovieInformation(boxOffice.code)
//                    boxOffice.thumb = movie.thumb
//                    adapter.notifyDataSetChanged()
//                }
            } catch (e: Exception) {
                Log.e("Exception", "Exception==>$e")
            }
        }
        return binding.root
    }
}