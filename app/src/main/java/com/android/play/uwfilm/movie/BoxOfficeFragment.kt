package com.android.play.uwfilm.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.play.uwfilm.R
import com.android.play.uwfilm.data.UWFilmApplication
import com.android.play.uwfilm.databinding.FragmentBoxOfficeBinding

/**
 * 일일 BoxOffice 목록 Fragment
 */
class BoxOfficeFragment : Fragment() {

    private lateinit var binding: FragmentBoxOfficeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_box_office, container, false)
        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
        }

        initBoxOfficeAdapter()
        return binding.root
    }

    private fun initBoxOfficeAdapter() {
        var adapter = BoxOfficeAdapter(mutableListOf())
        binding.boxOffices.adapter = adapter
        adapter.update(UWFilmApplication.getInstance().boxOfficeList)
        adapter.setItemClickListener { kobisMovieCode, title ->
            val fragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_KOBIS_MOVIE_CODE, kobisMovieCode)
                    putString(KEY_MOVIE_TITLE, title)
                }
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, null)
                .addToBackStack(null)
                .commit()
        }


    }
}