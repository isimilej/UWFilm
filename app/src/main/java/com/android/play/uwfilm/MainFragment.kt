package com.android.play.uwfilm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.databinding.FragmentMainBinding
import com.android.play.uwfilm.movie.MovieAdapter
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        var adapter = MovieAdapter(mutableListOf())
        adapter.setItemClickListener { movieCode ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailFragment(movieCode), null)
                .addToBackStack(null)
                .commit()
        }
        binding.movies.adapter = adapter

        lifecycleScope.launch {
            try {
                adapter.update(Movies().fetchNowPlayingList())
            } catch (e: Exception) {
                Log.e("Exception", "Exception==>$e")
            }
        }
        return binding.root
    }
}
