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
import com.nur_ikhsan.themoviedb.databinding.FragmentRecommendationBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailMovieActivity
import com.nur_ikhsan.themoviedb.ui.activity.DetailViewModel
import com.nur_ikhsan.themoviedb.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationFragment : Fragment() {

    private var _binding : FragmentRecommendationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
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
        viewModel.getRecommendationMovie(movieId).observe(viewLifecycleOwner){ rec->
            if (rec != null){
                val adapterMovies = AdapterMovies()
                adapterMovies.submitData(viewLifecycleOwner.lifecycle, rec)
                binding.apply {
                    rvRecommendation.adapter = adapterMovies
                    rvRecommendation.layoutManager = GridLayoutManager(context, 3)

                    btnRetry.setOnClickListener {
                        adapterMovies.retry()
                    }
                }

                adapterMovies.addLoadStateListener {
                    binding.apply {
                        tvError.isVisible = it.source.refresh is LoadState.Error
                        btnRetry.isVisible = it.source.refresh is LoadState.Error
                        rvRecommendation.isVisible = it.source.refresh is LoadState.NotLoading
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