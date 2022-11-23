package com.nur_ikhsan.themoviedb.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.databinding.FragmentDashboardBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.TrailersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFavorite()
    }

    private fun initFavorite() {
        val trailersAdapter = TrailersAdapter{
            if (it.isBookmarked){
                viewModel.deleteFavorite(it)
            }else{
                viewModel.saveFavorite(it)
            }
        }

        viewModel.getBookmarkState().observe(viewLifecycleOwner){
            trailersAdapter.submitList(it)
        }

        binding.rvFavorite.adapter = trailersAdapter
        binding.rvFavorite.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}