package com.nur_ikhsan.themoviedb.ui.watchlist

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.adapter.WatchlistAdapter
import com.nur_ikhsan.themoviedb.databinding.FragmentWatchlistBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WatchlistViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewModel.watchlist.observe(viewLifecycleOwner){ watchlist->
                val adapter = WatchlistAdapter(watchlist)
                binding.apply {
                    rvWatchlist.adapter = adapter
                    if (context?.applicationContext!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                        rvWatchlist.layoutManager = GridLayoutManager(context, 3)
                    }else{
                        rvWatchlist.layoutManager = GridLayoutManager(context, 6)
                    }
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}