package com.nur_ikhsan.themoviedb.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.databinding.ActivityKeywordBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeywordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKeywordBinding
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeywordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null){
            val keywordId = extras.getString(KEYWORD_ID)
            val keywordName = extras.getString(KEYWORD_NAME)
            if (keywordId != null && keywordName != null){

                initViewModel(keywordId, keywordName)
            }
        }
    }

    private fun initViewModel(keywordId: String, keywordName: String) {

        binding.toolbarMovie.title = keywordName
        binding.toolbarMovie.setNavigationOnClickListener {
            onBackPressed()
        }

        viewModel.getMovieByKeyword(keywordId = keywordId).observe(this){ keyword->
            if (keyword != null){
                val adapterMovies = AdapterMovies()
                adapterMovies.submitData(this.lifecycle, keyword)
                binding.apply {
                    rvResultKeyWord.adapter = adapterMovies
                    rvResultKeyWord.layoutManager = GridLayoutManager(this@KeywordActivity, 3)

                    btnRetry.setOnClickListener {
                        adapterMovies.retry()
                    }

                    adapterMovies.addLoadStateListener { loadState->
                        binding.apply {
                            tvError.isVisible = loadState.source.refresh is LoadState.Error
                            btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                            rvResultKeyWord.isVisible = loadState.source.refresh is LoadState.NotLoading
                        }
                    }
                }
            }
        }
    }

    companion object{
        const val KEYWORD_ID = "keyword_id"
        const val KEYWORD_NAME = "keyword_name"
    }
}