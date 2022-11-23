package com.nur_ikhsan.themoviedb.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.nur_ikhsan.themoviedb.BuildConfig
import com.nur_ikhsan.themoviedb.BuildConfig.URL_IMAGE
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.databinding.ActivityDetailMovieBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.PagerAdapter
import com.nur_ikhsan.themoviedb.ui.watchlist.WatchlistViewModel
import com.nur_ikhsan.themoviedb.utils.DateFormat
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
    private val detailViewModel by viewModels<DetailViewModel>()

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
                initToolbar(titleMovie, movieId)
                initWatchlist(movieId, titleMovie, posterPath)
                initViewModel(movieId)
            }
        }

        initPagerAdapter()
    }

    private fun initViewModel(movieId: String) {
        detailViewModel.detailMovie(movie_id = movieId).observe(this){ detail->
            if (detail != null){

                Glide.with(this)
                    .load(Uri.parse(URL_IMAGE + detail.backdropPath))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.color.secondary_color)
                    .transition(DrawableTransitionOptions.withCrossFade(100))
                    .into(binding.imageBackdropPath)


                Glide.with(this)
                    .load(Uri.parse(BuildConfig.URL_IMAGE + detail.posterPath))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(DrawableTransitionOptions.withCrossFade(100))
                    .transform(RoundedCorners(30))
                    .into(binding.imageMovie)

                binding.apply {

                    tvTitleMovie.text = detail.title
                    tvTitleMovie.isSelected = true
                    tvVoteAverage.text = detail.voteAverage.toString()
                    tvReleaseDate.text = DateFormat.formatDate(detail.releaseDate, "dd, MMMM yyyy")
                }


                val homePage = detail.homepage
                binding.btnHomePage.setOnClickListener {
                    if (homePage.isNotEmpty()){
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(homePage)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "No homepage for ${detail.title}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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
                Toast.makeText(this, "$titleMovie, berhasil ditambahkan ke watchlist", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.removeFromWatchlist(movieId)
                Toast.makeText(this, "$titleMovie, berhasil dihapus dari watchlist", Toast.LENGTH_SHORT).show()
            }
            binding.btnWatchlist.isChecked = isCheck
        }
    }


    private fun initPagerAdapter() {
        pagerAdapter = PagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerDetail.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayoutDetail, binding.viewPagerDetail){ tab, position->
            when(position){
                0-> tab.text = "About"
            }
        }.attach()
    }

    private fun initToolbar(titleMovie: String, movieId : String) {
        binding.apply {

            setSupportActionBar(toolbarMovie)
            assert(supportActionBar != null)
            supportActionBar?.title = titleMovie
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            toolbarBottomDetail.setOnMenuItemClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "$SHARE$movieId")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(Intent.createChooser(intent, "Share with..."))
                true
            }

            btnOpenWith.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("$WATCH$movieId")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }

    companion object{
        const val TITLE_MOVIE = "title_movie"
        const val MOVIE_ID = "movie_id"
        const val POSTER_PATH = "poster_path"
        const val SHARE = "https://www.themoviedb.org/movie/"
        const val WATCH = "https://www.themoviedb.org/movie/"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}