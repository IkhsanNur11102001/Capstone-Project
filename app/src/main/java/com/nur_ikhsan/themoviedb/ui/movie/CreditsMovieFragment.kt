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
import com.nur_ikhsan.themoviedb.databinding.FragmentCreditsMovieBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailCreditsActivity
import com.nur_ikhsan.themoviedb.ui.activity.DetailViewModel
import com.nur_ikhsan.themoviedb.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditsMovieFragment : Fragment() {

    private var _binding : FragmentCreditsMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreditsMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val extras = activity?.intent?.extras
        if (extras != null){
            val withPeople = extras.getString(DetailCreditsActivity.CREDITS_ID)
            if (withPeople != null){
                viewModel.getMovieByCredits(with_people = withPeople).observe(viewLifecycleOwner){ credits->
                    if (credits != null){
                        val adapterMovies = AdapterMovies()
                        adapterMovies.submitData(viewLifecycleOwner.lifecycle, credits)
                        binding.apply {
                            rvMovieCredits.adapter = adapterMovies
                            rvMovieCredits.layoutManager = GridLayoutManager(context, 3)

                            btnRetry.setOnClickListener {
                                adapterMovies.retry()
                            }
                        }

                        adapterMovies.addLoadStateListener {
                            binding.apply {
                                tvError.isVisible = it.source.refresh is LoadState.Error
                                btnRetry.isVisible = it.source.refresh is LoadState.Error
                                rvMovieCredits.isVisible = it.source.refresh is LoadState.NotLoading
                            }
                        }
                    }
                }
            }
        }
    }
}