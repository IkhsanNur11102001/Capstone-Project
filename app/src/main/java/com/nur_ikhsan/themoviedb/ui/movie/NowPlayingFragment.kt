package com.nur_ikhsan.themoviedb.ui.movie

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
import com.nur_ikhsan.themoviedb.data.paging.adapter.MovieLoadStateAdapter
import com.nur_ikhsan.themoviedb.databinding.FragmentNowPlayingBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment : Fragment() {

    private var _binding : FragmentNowPlayingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPopularMovies()

        binding.toolbarMovie.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    //Top rated movies
    private fun initPopularMovies() {
        val adapterMovies = AdapterMovies()
        viewModel.nowPlaying.observe(viewLifecycleOwner){ topRated->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, topRated)
        }

        binding.apply {
            rvNowPlaying.adapter = adapterMovies.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter{ adapterMovies.retry()},
                footer = MovieLoadStateAdapter{ adapterMovies.retry()}
            )
          if (context?.applicationContext!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
              rvNowPlaying.layoutManager = GridLayoutManager(context, 3)
          }else{
              rvNowPlaying.layoutManager = GridLayoutManager(context, 6)
          }
        }

        binding.apply {
            btnRetry.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvError.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetry.isVisible = loadSate.source.refresh is LoadState.Error
                rvNowPlaying.isVisible = loadSate.source.refresh is LoadState.NotLoading
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}