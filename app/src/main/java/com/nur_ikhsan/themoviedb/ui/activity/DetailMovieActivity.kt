package com.nur_ikhsan.themoviedb.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.nur_ikhsan.themoviedb.databinding.ActivityDetailMovieBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.PagerAdapter
import com.nur_ikhsan.themoviedb.ui.watchlist.WatchlistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailMovieBinding
    private lateinit var pagerAdapter: PagerAdapter
    private val viewModel by viewModels<WatchlistViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null){
            val movieId = extras.getString(MOVIE_ID)
            val titleMovie = extras.getString(TITLE_MOVIE)
            val posterPath = extras.getString(POSTER_PATH)
            if (movieId != null && titleMovie != null && posterPath != null){
                initToolbar(titleMovie)
                initWatchlist(movieId, titleMovie, posterPath)
            }
        }

        initPagerAdapter()
    }

    private fun initWatchlist(movieId: String, titleMovie: String, posterPath : String) {
        var isCheck = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkWatchlistMovie(id = movieId)
            withContext(Main){
                if (count > 0){
                    binding.btnWatchlist.isChecked = true
                    isCheck = true
                }else{
                    binding.btnWatchlist.isChecked = false
                    isCheck = false
                }
            }
        }

        binding.btnWatchlist.setOnClickListener {
            isCheck = !isCheck
            if (isCheck) {
                viewModel.addToWatchlist(titleMovie, movieId, posterPath)
                message("$titleMovie berhasil ditambahkan ke watchlist")
            }else{
                viewModel.removeFromWatchlist(movieId)
                message("$titleMovie berhasil dihapus dari watchlist")
            }
            binding.btnWatchlist.isChecked = isCheck
        }
    }


    private fun initPagerAdapter() {
        pagerAdapter = PagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerDetail.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayoutDetail, binding.viewPagerDetail){ tab, position->
            when(position){
                0-> tab.text = "About Movie"
            }
        }.attach()
    }

    private fun initToolbar(titleMovie: String) {
        binding.apply {

            setSupportActionBar(toolbarMovie)
            assert(supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)

            tvTitleMovie.isSelected = true
            tvTitleMovie.text = titleMovie
        }
    }

    private fun message(message : String){
        Snackbar.make(binding.tvTitleMovie, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object{
        const val TITLE_MOVIE = "title_movie"
        const val MOVIE_ID = "movie_id"
        const val POSTER_PATH = "poster_path"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}