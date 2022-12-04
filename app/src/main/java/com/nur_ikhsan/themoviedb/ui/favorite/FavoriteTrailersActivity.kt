package com.nur_ikhsan.themoviedb.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.adapter.TrailersAdapter
import com.nur_ikhsan.themoviedb.databinding.ActivityFavoriteTrailersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTrailersActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteTrailersBinding
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteTrailersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarMovie.setNavigationOnClickListener {
            onBackPressed()
        }
        initFavoriteTrailers()
    }

    private fun initFavoriteTrailers() {
        val trailersAdapter = TrailersAdapter{
            if (it.isBookmarked){
                viewModel.deleteFavorite(it)
            }else{
                viewModel.saveFavorite(it)
            }
        }
        viewModel.getBookmarkState().observe(this){ favorite->
            trailersAdapter.submitList(favorite)
        }

        binding.apply {
            rvFavoriteTrailers.adapter = trailersAdapter
            rvFavoriteTrailers.layoutManager = GridLayoutManager(this@FavoriteTrailersActivity, 2)
        }
    }
}