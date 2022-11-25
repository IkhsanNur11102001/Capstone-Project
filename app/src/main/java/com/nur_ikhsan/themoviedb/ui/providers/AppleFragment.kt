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
import com.nur_ikhsan.themoviedb.databinding.FragmentAppleBinding
import com.nur_ikhsan.themoviedb.ui.movie.MovieViewModel
import com.nur_ikhsan.themoviedb.ui.movie.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppleFragment : Fragment() {

    private var _binding : FragmentAppleBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppleBinding.inflate(inflater, container, false)
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
        viewModel.appleMovies.observe(viewLifecycleOwner){ apple->
            val adapterMovies = AdapterMovies()
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, apple)
            binding.apply {
                rvApple.adapter = adapterMovies

                rvApple.layoutManager = GridLayoutManager(context, 3)
                adapterMovies.addLoadStateListener { loadState->
                    binding.apply {
                        tvError.isVisible = loadState.source.refresh is LoadState.Error
                        btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                        rvApple.isVisible = loadState.source.refresh is LoadState.NotLoading

                        if (loadState.source.refresh is LoadState.NotLoading &&
                            loadState.append.endOfPaginationReached && adapterMovies.itemCount < 1){
                            rvApple.isVisible = false
                            tvError.isVisible = true
                        }else{
                            tvError.isVisible = false
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}