package com.nur_ikhsan.themoviedb.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.adapter.FavoriteMoviesAdapter
import com.nur_ikhsan.themoviedb.databinding.FragmentFavoriteMoviesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    private var _binding : FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniFavoriteMovies()
    }

    private fun iniFavoriteMovies() {
        viewModel.favorites.observe(viewLifecycleOwner){ favorite->
            val adapterFavorite = FavoriteMoviesAdapter(favorite)
            binding.apply {
                rvFavoriteMovies.adapter = adapterFavorite
                rvFavoriteMovies.layoutManager = GridLayoutManager(context, 3)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}