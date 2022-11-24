package com.nur_ikhsan.themoviedb.ui.providers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.data.paging.adapter.MovieLoadStateAdapter
import com.nur_ikhsan.themoviedb.databinding.FragmentHBOBinding
import com.nur_ikhsan.themoviedb.ui.movie.MovieViewModel
import com.nur_ikhsan.themoviedb.ui.movie.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HBOFragment : Fragment() {

    private var _binding : FragmentHBOBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHBOBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarMovie.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.hboMax.observe(viewLifecycleOwner){ hbo->
            val adapterMovies = AdapterMovies()
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, hbo)
            binding.apply {
                rvHBO.adapter = adapterMovies.withLoadStateHeaderAndFooter(
                    header = MovieLoadStateAdapter{adapterMovies.retry()},
                    footer = MovieLoadStateAdapter{adapterMovies.retry()}
                )
                rvHBO.layoutManager = GridLayoutManager(context, 3)
            }
            adapterMovies.addLoadStateListener { loadState->
                binding.apply {
                    tvError.isVisible = loadState.source.refresh is LoadState.Error
                    btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                    rvHBO.isVisible = loadState.source.refresh is LoadState.NotLoading
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}