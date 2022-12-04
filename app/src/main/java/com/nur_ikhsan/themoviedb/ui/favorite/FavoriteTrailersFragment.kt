package com.nur_ikhsan.themoviedb.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.adapter.TrailersAdapter
import com.nur_ikhsan.themoviedb.databinding.FragmentFavoriteTrailersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTrailersFragment : Fragment() {

    private var _binding : FragmentFavoriteTrailersBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteTrailersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trailersAdapter = TrailersAdapter{ trailers->
            if (trailers.isBookmarked){
                viewModel.deleteFavorite(trailers)
            }else{
                viewModel.saveFavorite(trailers)
            }
        }

        viewModel.getBookmarkState().observe(viewLifecycleOwner){ favorite->
            trailersAdapter.submitList(favorite)
        }

        binding.apply {
            rvFavoriteTrailers.adapter = trailersAdapter
            rvFavoriteTrailers.layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}