package com.nur_ikhsan.themoviedb.ui.providers

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.databinding.FragmentNetflixBinding
import com.nur_ikhsan.themoviedb.ui.movie.MovieViewModel
import com.nur_ikhsan.themoviedb.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetflixFragment : Fragment() {

    private var _binding : FragmentNetflixBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentNetflixBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.netflixMovie.observe(viewLifecycleOwner){ netflix->
            val adapterMovies = AdapterMovies()
            if (netflix != null){
                adapterMovies.submitData(viewLifecycleOwner.lifecycle, netflix)
                binding.apply {
                    rvNetflix.adapter = adapterMovies

                    if (context?.applicationContext!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                        rvNetflix.layoutManager = GridLayoutManager(context, 3)
                    }else{
                        rvNetflix.layoutManager = GridLayoutManager(context, 6)
                    }

                    btnRetry.setOnClickListener {
                        adapterMovies.retry()
                    }

                    toolbarMovie.setNavigationOnClickListener {
                        requireActivity().onBackPressed()
                    }

                    adapterMovies.addLoadStateListener { loadState->
                        binding.apply {
                            tvError.isVisible = loadState.source.refresh is LoadState.Error
                            btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                            rvNetflix.isVisible = loadState.source.refresh is LoadState.NotLoading

                            if (loadState.source.refresh is LoadState.NotLoading &&
                                loadState.append.endOfPaginationReached && adapterMovies.itemCount < 1){
                                rvNetflix.isVisible = false
                                tvError.isVisible = true
                            }else{
                                tvError.isVisible = false
                            }
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