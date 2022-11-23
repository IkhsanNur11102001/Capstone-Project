package com.nur_ikhsan.themoviedb.ui.providers

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.data.paging.adapter.MovieLoadStateAdapter
import com.nur_ikhsan.themoviedb.databinding.ActivityMovieProvidersBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieProvidersActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMovieProvidersBinding
    private val viewModel by viewModels<ProvidersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieProvidersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null){
            val providersId = extras.getString(PROVIDERS_ID)
            val providersName = extras.getString(PROVIDERS_NAME)
            if (providersId != null && providersName != null){
                initViewModel(providersId, providersName)
            }
        }
    }

    private fun initViewModel(providersId: String, providersName: String) {
        setSupportActionBar(binding.toolbarMovie)
        assert(supportActionBar != null)
        supportActionBar?.title = providersName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarMovie.setNavigationOnClickListener {
            onBackPressed()
        }

        viewModel.getMovieByProviders(providersId).observe(this){ providers->
            val adapterMovies = AdapterMovies()
            adapterMovies.submitData(this.lifecycle, providers)
            binding.rvProviders.adapter = adapterMovies.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter{adapterMovies.retry()},
                footer = MovieLoadStateAdapter{adapterMovies.retry()}
            )

            binding.btnRetry.setOnClickListener {
                adapterMovies.retry()
            }

            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                binding.rvProviders.layoutManager = GridLayoutManager(this, 3)
            }else{
                binding.rvProviders.layoutManager = GridLayoutManager(this, 6)
            }


            adapterMovies.addLoadStateListener { loadState->
                binding.apply {
                    tvError.isVisible = loadState.source.refresh is LoadState.Error
                    btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                    rvProviders.isVisible = loadState.source.refresh is LoadState.NotLoading
                }
            }
        }
    }

    companion object{
        const val PROVIDERS_ID = "providers_id"
        const val PROVIDERS_NAME = "providers_name"
    }
}