package com.nur_ikhsan.themoviedb.ui.activity


import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.data.paging.adapter.MovieLoadStateAdapter
import com.nur_ikhsan.themoviedb.databinding.ActivityGenresBinding
import com.nur_ikhsan.themoviedb.ui.genres.GenresViewModel
import com.nur_ikhsan.themoviedb.ui.movie.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenresActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGenresBinding
    private val viewModel by viewModels<GenresViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            val genresId = extras.getString(GENRES_ID)
            val genresName = extras.getString(GENRES_NAME)

            if (genresId != null && genresName != null) {
                viewModel.movieByGenres(genresId).observe(this) { genres ->
                    val adapterMovies = AdapterMovies()
                    adapterMovies.submitData(this.lifecycle, genres)

                    binding.rvGenres.adapter = adapterMovies.withLoadStateHeaderAndFooter(
                        header = MovieLoadStateAdapter { adapterMovies.retry() },
                        footer = MovieLoadStateAdapter { adapterMovies.retry() })

                    if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        binding.rvGenres.layoutManager = GridLayoutManager(this, 3)
                    }else{
                        binding.rvGenres.layoutManager = GridLayoutManager(this, 6)
                    }

                    binding.btnRetry.setOnClickListener {
                        adapterMovies.retry()
                    }
                    adapterMovies.addLoadStateListener { loadState ->
                        binding.apply {
                            tvError.isVisible = loadState.source.refresh is LoadState.Error
                            btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                            rvGenres.isVisible = loadState.source.refresh is LoadState.NotLoading
                        }
                    }
                    setSupportActionBar(binding.toolbarMovie)
                    supportActionBar?.title = genresName
                    assert(supportActionBar != null)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }


    companion object{
        const val GENRES_ID = "id"
        const val GENRES_NAME = "name"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}