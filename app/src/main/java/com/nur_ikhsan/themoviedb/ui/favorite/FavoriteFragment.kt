package com.nur_ikhsan.themoviedb.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.nur_ikhsan.themoviedb.adapter.PagerAdapterFavorite
import com.nur_ikhsan.themoviedb.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagerAdapterFavorite: PagerAdapterFavorite

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPagerAdapter()
    }

    private fun initViewPagerAdapter() {
        pagerAdapterFavorite = PagerAdapterFavorite(childFragmentManager, lifecycle)
        binding.apply {
            viewPagerFavorite.adapter = pagerAdapterFavorite
            TabLayoutMediator(tabLayoutFavorite, viewPagerFavorite){ tab, position->
                when(position){
                    0-> tab.text = "Trailers"
                    1-> tab.text = "Movies"
                }
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}