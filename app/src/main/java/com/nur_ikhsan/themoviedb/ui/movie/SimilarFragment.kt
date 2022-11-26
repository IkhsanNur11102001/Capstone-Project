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
import com.nur_ikhsan.themoviedb.databinding.FragmentSimilarBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailMovieActivity
import com.nur_ikhsan.themoviedb.ui.activity.DetailViewModel
import com.nur_ikhsan.themoviedb.ui.movie.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimilarFragment : Fragment() {

    private var _binding : FragmentSimilarBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSimilarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val extras = activity?.intent?.extras
        if (extras != null){
            val movieId = extras.getString(DetailMovieActivity.MOVIE_ID)
            if (movieId != null){
                initViewModel(movieId)
            }
        }
    }

    private fun initViewModel(movieId: String) {
        viewModel.getSimilar(movie_id = movieId).observe(viewLifecycleOwner){ similar->
            if (similar != null){
                val adapterMovies = AdapterMovies()
                adapterMovies.submitData(viewLifecycleOwner.lifecycle, similar)
                binding.apply {
                    rvSimilar.adapter = adapterMovies
                    rvSimilar.layoutManager = GridLayoutManager(context, 3)

                    btnRetry.setOnClickListener {
                        adapterMovies.retry()
                    }
                }
                adapterMovies.addLoadStateListener { loadState->
                    binding.apply {
                        tvError.isVisible = loadState.source.refresh is LoadState.Error
                        btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                        rvSimilar.isVisible = loadState.source.refresh is LoadState.NotLoading
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