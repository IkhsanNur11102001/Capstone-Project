package com.nur_ikhsan.themoviedb.ui.movie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nur_ikhsan.themoviedb.ui.movie.AboutFragment
import com.nur_ikhsan.themoviedb.ui.movie.CastFragment
import com.nur_ikhsan.themoviedb.ui.movie.CrewFragment

class PagerAdapter(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position){
            0-> fragment = AboutFragment()
            1-> fragment = CastFragment()
            2-> fragment = CrewFragment()
        }
        return fragment
    }
}