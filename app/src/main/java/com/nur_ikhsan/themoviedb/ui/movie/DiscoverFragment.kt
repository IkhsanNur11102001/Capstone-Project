package com.nur_ikhsan.themoviedb.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.databinding.FragmentDiscoverBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private var _binding : FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarMovie.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.toolbarMovie.setOnMenuItemClickListener {
           when(it.itemId){
               R.id.year_2022 -> discover2022()
               R.id.year_2020 ->discover2020()
           }
            it.isChecked = true
            true
        }
        discover2022()
    }

    private fun discover2020() {
        viewModel.discover2020.observe(viewLifecycleOwner){ discover2020->
            val adapterMovies = AdapterMovies()
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, discover2020)
            binding.rvDiscover.adapter = adapterMovies

            binding.btnRetry.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadState->
                binding.apply {
                    tvError.isVisible = loadState.source.refresh is LoadState.Error
                    btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                    rvDiscover.isVisible = loadState.source.refresh is LoadState.NotLoading

                    if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached && adapterMovies.itemCount < 1){
                        rvDiscover.isVisible = false
                        tvError.isVisible = true
                    }else{
                        tvError.isVisible = false
                    }
                }
            }
            binding.rvDiscover.layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun discover2022() {
        viewModel.discover2022.observe(viewLifecycleOwner){ discover2022->
            val adapterMovies = AdapterMovies()
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, discover2022)
            binding.rvDiscover.adapter = adapterMovies

            binding.btnRetry.setOnClickListener {
                adapterMovies.retry()
            }

            binding.rvDiscover.layoutManager = GridLayoutManager(context, 3)

            adapterMovies.addLoadStateListener { loadState->
                binding.apply {
                    tvError.isVisible = loadState.source.refresh is LoadState.Error
                    btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                    rvDiscover.isVisible = loadState.source.refresh is LoadState.NotLoading
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}