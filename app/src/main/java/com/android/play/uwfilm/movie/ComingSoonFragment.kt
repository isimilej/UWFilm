package com.android.play.uwfilm.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.play.uwfilm.R
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.databinding.FragmentComingSoonBinding
import kotlinx.coroutines.launch

class ComingSoonFragment : Fragment() {

    private lateinit var binding: FragmentComingSoonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coming_soon, container, false)

//        lifecycleScope.launch {
//            var movies = Movies().fetchComingSoonList()
//        }

        return binding.root
    }

}