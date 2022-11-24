package com.nur_ikhsan.themoviedb.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nur_ikhsan.themoviedb.databinding.FragmentCastBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailMovieActivity
import com.nur_ikhsan.themoviedb.ui.activity.DetailViewModel
import com.nur_ikhsan.themoviedb.ui.movie.adapter.CastAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CastFragment : Fragment() {

    private var _binding : FragmentCastBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCastBinding.inflate(inflater, container, false)
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
        viewModel.getCastMovie(movie_id = movieId).observe(viewLifecycleOwner){ cast->
            if (cast != null){
                val castAdapter = CastAdapter()
                castAdapter.submitList(cast)
                binding.apply {
                    rvCast.adapter = castAdapter
                    rvCast.layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}