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
import com.nur_ikhsan.themoviedb.databinding.FragmentPopularBinding
import com.nur_ikhsan.themoviedb.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private var _binding : FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
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
        viewModel.popular.observe(viewLifecycleOwner){ topRated->
            adapterMovies.submitData(viewLifecycleOwner.lifecycle, topRated)
        }

        binding.apply {
            rvPopular.adapter = adapterMovies

          if (context?.applicationContext!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
              rvPopular.layoutManager = GridLayoutManager(context, 3)
          }else{
              rvPopular.layoutManager = GridLayoutManager(context, 6)
          }
        }

        binding.apply {
            btnRetry.setOnClickListener {
                adapterMovies.retry()
            }

            adapterMovies.addLoadStateListener { loadSate->
                tvError.isVisible = loadSate.source.refresh is LoadState.Error
                btnRetry.isVisible = loadSate.source.refresh is LoadState.Error
                rvPopular.isVisible = loadSate.source.refresh is LoadState.NotLoading

                if (loadSate.source.refresh is LoadState.NotLoading &&
                    loadSate.append.endOfPaginationReached && adapterMovies.itemCount < 1){
                    rvPopular.isVisible = false
                    tvError.isVisible = true
                }else{
                    tvError.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}