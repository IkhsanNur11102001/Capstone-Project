package com.nur_ikhsan.themoviedb.ui.watchlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.adapter.WatchlistAdapter
import com.nur_ikhsan.themoviedb.databinding.ActivityWatchlistMoviesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WatchlistMoviesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWatchlistMoviesBinding
    private val viewModel by viewModels<WatchlistViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchlistMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarMovie.setNavigationOnClickListener {
            onBackPressed()
        }
        initWatchlist()
    }

    private fun initWatchlist() {
        viewModel.watchlist.observe(this){ watchlist->
            val watchlistAdapter = WatchlistAdapter(watchlist)
            binding.apply {
                rvWatchlist.adapter = watchlistAdapter
                rvWatchlist.layoutManager = GridLayoutManager(this@WatchlistMoviesActivity, 3)
            }
        }
    }
}