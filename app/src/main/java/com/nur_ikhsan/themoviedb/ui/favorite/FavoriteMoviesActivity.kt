package com.nur_ikhsan.themoviedb.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.adapter.FavoriteMoviesAdapter
import com.nur_ikhsan.themoviedb.databinding.ActivityFavoriteMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteMoviesBinding
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarMovie.setNavigationOnClickListener {
            onBackPressed()
        }
        initFavoriteMovies()
    }

    private fun initFavoriteMovies() {
        viewModel.favorites.observe(this){ favorite->
            val adapterFavorite = FavoriteMoviesAdapter(favorite)
            binding.apply {
                rvFavoriteMovies.layoutManager = GridLayoutManager(this@FavoriteMoviesActivity, 3)
                rvFavoriteMovies.adapter = adapterFavorite
            }
        }
    }
}