package com.nur_ikhsan.themoviedb.ui.movie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nur_ikhsan.themoviedb.ui.movie.*

class PagerAdapterDetailCredits(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position){
            0-> fragment = AboutCreditsFragment()
            1-> fragment = CreditsMovieFragment()
        }
        return fragment
    }
}