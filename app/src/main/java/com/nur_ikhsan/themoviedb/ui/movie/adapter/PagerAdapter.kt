package com.nur_ikhsan.themoviedb.ui.movie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nur_ikhsan.themoviedb.ui.movie.*

class PagerAdapter(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position){
            0-> fragment = AboutFragment()
            1-> fragment = CastFragment()
            2-> fragment = CrewFragment()
            3-> fragment = RecommendationFragment()
            4-> fragment = SimilarFragment()
        }
        return fragment
    }
}