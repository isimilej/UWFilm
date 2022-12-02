package com.android.play.uwfilm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.play.uwfilm.databinding.FragmentMainBinding
import com.android.play.uwfilm.movie.BoxOfficeFragment
import com.android.play.uwfilm.movie.ComingSoonFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.pager.adapter = PagerAdapter(requireActivity())

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> "BOX OFFICE"
                else -> "COMING SOON"
            }
        }.attach()

        binding.pager.isUserInputEnabled = false
        return binding.root
    }

    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> BoxOfficeFragment()
            else -> ComingSoonFragment()
        }
    }
}
