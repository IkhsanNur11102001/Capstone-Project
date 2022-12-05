package com.nur_ikhsan.themoviedb.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.databinding.FragmentMovieBinding
import com.nur_ikhsan.themoviedb.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTopRatedMovies()
        initPopularMovies()
        initUpComingMovies()
        initNowPlayingMovies()
        initToolbar()
        initTextView()
        initNetflixMovie()
        initAppleTv()
        initDisney()
        initHBOmax()
        initParamount()
    }


    private fun initTextView() {
        binding.apply {
            tvTopRated.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_topRatedFragment)
            }
            tvPopular.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_popularFragment)
            }
            tvUpComing.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_upComingFragment)
            }
            tvNowPlaying.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_nowPlayingFragment)
            }
            tvNetflix.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_netflixFragment)
            }
            tvApple.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_appleFragment)
            }
            tvDisney.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_disneyFragment)
            }
            tvHBO.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_HBOFragment)
            }
            tvParamount.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_paramountFragment)
            }
        }
    }

    private fun initToolbar() {
        binding.apply {
            toolbarMovie.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_profileFragment)
            }

            toolbarMovie.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.navigation_search ->
                        findNavController().navigate(R.id.action_navigation_home_to_searchFragment)
                }
                true
            }
        }
    }

    //paramount
    private fun initParamount() {
        viewModel.paramount.observe(viewLifecycleOwner){ paramount->
            if (paramount != null){
                val adapterMovies = AdapterMovies()
                adapterMovies.submitData(viewLifecycleOwner.lifecycle, paramount)
                binding.apply {
                    rvParamount.adapter = adapterMovies
                    rvParamount.layoutManager = LinearLayoutManager(context,
                        LinearLayoutManager.HORIZONTAL, false)
                    btnRetryParamount.setOnClickListener {
                        adapterMovies.retry()
                    }
                    adapterMovies.addLoadStateListener { loadState->
                        binding.apply {
                            tvErrorParamount.isVisible = loadState.source.refresh is LoadState.Error
                            btnRetryParamount.isVisible = loadState.source.refresh is LoadState.Error
                            rvParamount.isVisible = loadState.source.refresh is LoadState.NotLoading
                        }
                    }
                }
            }
        }
    }


    //HBO max
    private fun initHBOmax() {
        val adapterMovies = AdapterMovies()
        viewModel.hboMax.observe(viewLifecycleOwner){ hbo->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, hbo)
        }
        binding.apply {
            binding.rvHBO.adapter = adapterMovies
            binding.rvHBO.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.apply {
            btnRetryHBO.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvErrorHBO.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetryHBO.isVisible = loadSate.source.refresh is LoadState.Error
                rvHBO.isVisible = loadSate.source.refresh is LoadState.NotLoading
            }
        }
    }


    //Disney plus
    private fun initDisney() {
        val adapterMovies = AdapterMovies()
        viewModel.disneyMovies.observe(viewLifecycleOwner){ disney->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, disney)
        }
        binding.apply {
            binding.rvDisney.adapter = adapterMovies
            binding.rvDisney.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.apply {
            btnRetryDisney.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvErrorDisney.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetryDisney.isVisible = loadSate.source.refresh is LoadState.Error
                rvDisney.isVisible = loadSate.source.refresh is LoadState.NotLoading
            }
        }
    }


    //apple tv
    private fun initAppleTv() {
        val adapterMovies = AdapterMovies()
        viewModel.appleMovies.observe(viewLifecycleOwner){ apple->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, apple)
        }
        binding.apply {
            binding.rvApple.adapter = adapterMovies
            binding.rvApple.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.apply {
            btnRetryApple.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvErrorApple.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetryApple.isVisible = loadSate.source.refresh is LoadState.Error
                rvApple.isVisible = loadSate.source.refresh is LoadState.NotLoading
            }
        }
    }


    //netflix movies
    private fun initNetflixMovie() {
        val adapterMovies = AdapterMovies()
        viewModel.netflixMovie.observe(viewLifecycleOwner){ netflix->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, netflix)
        }
        binding.apply {
            binding.rvNetflix.adapter = adapterMovies
            binding.rvNetflix.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.apply {
            btnRetryNetflix.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvErrorNetflix.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetryNetflix.isVisible = loadSate.source.refresh is LoadState.Error
                rvNetflix.isVisible = loadSate.source.refresh is LoadState.NotLoading
            }
        }
    }

    // Now playing movies
    private fun initNowPlayingMovies() {
        val adapterMovies = AdapterMovies()
        viewModel.nowPlaying.observe(viewLifecycleOwner){ nowPlaying->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, nowPlaying)
        }

        binding.apply {
            rvNowPlaying.adapter = adapterMovies
            rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false)
        }

        binding.apply {
            btnRetryNowPlaying.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvErrorNowPlaying.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetryNowPlaying.isVisible = loadSate.source.refresh is LoadState.Error
                rvNowPlaying.isVisible = loadSate.source.refresh is LoadState.NotLoading
            }
        }
    }


    //Up coming movies
    private fun initUpComingMovies() {
        val adapterMovies = AdapterMovies()
        viewModel.upComing.observe(viewLifecycleOwner){ upComing->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, upComing)
        }
        binding.apply {
            rvUpComing.adapter = adapterMovies
            rvUpComing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false)
        }

        binding.apply {
            btnRetryUpComing.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvErrorUpComing.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetryUpComing.isVisible = loadSate.source.refresh is LoadState.Error
                rvUpComing.isVisible = loadSate.source.refresh is LoadState.NotLoading
            }
        }
    }


    //popular movies
    private fun initPopularMovies() {
        val adapterMovies = AdapterMovies()
        viewModel.popular.observe(viewLifecycleOwner){ popular->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, popular)
        }
        binding.apply {
            rvPopular.adapter = adapterMovies
            rvPopular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false)
        }

        binding.apply {
            btnRetryPopular.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvErrorPopular.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetryPopular.isVisible = loadSate.source.refresh is LoadState.Error
                rvPopular.isVisible = loadSate.source.refresh is LoadState.NotLoading
            }
        }
    }


    //Top rated movies
    private fun initTopRatedMovies() {
        val adapterMovies = AdapterMovies()
        viewModel.topRated.observe(viewLifecycleOwner){ topRated->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, topRated)
        }

        binding.apply {
            rvTopRated.adapter = adapterMovies
            rvTopRated.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false)
        }

        binding.apply {
            btnRetryTopRated.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvErrorTopRated.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetryTopRated.isVisible = loadSate.source.refresh is LoadState.Error
                rvTopRated.isVisible = loadSate.source.refresh is LoadState.NotLoading
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}