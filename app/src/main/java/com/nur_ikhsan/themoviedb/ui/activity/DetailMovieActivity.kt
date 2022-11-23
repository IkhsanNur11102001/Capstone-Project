package com.nur_ikhsan.themoviedb.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.nur_ikhsan.themoviedb.BuildConfig.URL_IMAGE
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.databinding.ActivityDetailMovieBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.PagerAdapter
import com.nur_ikhsan.themoviedb.ui.providers.ProvidersAdapterBuy
import com.nur_ikhsan.themoviedb.ui.providers.ProvidersAdapterRent
import com.nur_ikhsan.themoviedb.ui.providers.ProvidersAdapterStream
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

    //bottom sheet
    private lateinit var bottomSheet : BottomSheetDialog

    //recycler view
    private lateinit var rvStream : RecyclerView
    private lateinit var rvBuy : RecyclerView
    private lateinit var rvRent : RecyclerView

    //imageView
    private lateinit var imageBack : ImageView

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

                binding.btnProviders.setOnClickListener {
                    detailViewModel.getProvidersMovie(movieId).observe(this){ providers->
                        if (providers?.uS != null){
                            initBottomSheeT(movieId)
                        }else{
                            Toast.makeText(this, "Watch providers for $titleMovie is not available",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        initPagerAdapter()
    }

    @SuppressLint("InflateParams")
    private fun initBottomSheeT(movieId: String) {

        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_item, null)
        bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(dialogView)
        bottomSheet.show()

        //recycler view
        rvStream = dialogView.findViewById(R.id.rvStream)
        rvBuy = dialogView.findViewById(R.id.rvBuy)
        rvRent = dialogView.findViewById(R.id.rvRent)

        //image View
        imageBack = dialogView.findViewById(R.id.btnBack)

        imageBack.setOnClickListener {
            bottomSheet.hide()
        }

        //stream
        detailViewModel.getProvidersMovie(movieId).observe(this){ stream->
            if (stream?.uS?.stream != null){
                val providersAdapterStream = ProvidersAdapterStream(stream)
                rvStream.adapter = providersAdapterStream
                rvStream.layoutManager = GridLayoutManager(this, 4)

                providersAdapterStream.onSelectData(object : ProvidersAdapterStream.OnSelectData{
                    override fun itemClicked(link: String) {
                        Intent(Intent.ACTION_VIEW).also { intent ->
                            intent.data = Uri.parse(link)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }
                    }
                })
            }else{
                rvStream.isVisible = false
            }
        }

        //buy
        detailViewModel.getProvidersMovie(movieId).observe(this){ buy->
            if (buy?.uS?.buy != null){
                val providersAdapterBuy = ProvidersAdapterBuy(buy)
                rvBuy.adapter = providersAdapterBuy
                rvBuy.layoutManager = GridLayoutManager(this, 4)

                providersAdapterBuy.onSelectData(object : ProvidersAdapterBuy.OnSelectData{
                    override fun itemClicked(link: String) {
                        Intent(Intent.ACTION_VIEW).also { intent ->
                            intent.data = Uri.parse(link)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }
                    }
                })
            }else{
                rvBuy.isVisible = false
            }
        }

        //rent
        detailViewModel.getProvidersMovie(movieId).observe(this){ rent->
            if (rent?.uS?.rent != null){
                val providersAdapterRent = ProvidersAdapterRent(rent)
                rvRent.adapter = providersAdapterRent
                rvRent.layoutManager = GridLayoutManager(this, 4)

                providersAdapterRent.onSelectData(object : ProvidersAdapterRent.OnSelectData{
                    override fun itemClicked(link: String) {
                        Intent(Intent.ACTION_VIEW).also { intent ->
                            intent.data = Uri.parse(link)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }
                    }
                })
            }else{
                rvRent.isVisible = false
            }
        }
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
                    .load(Uri.parse("$URL_IMAGE${detail.posterPath}"))
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
                when(it.itemId){

                    R.id.btn_share -> Intent(Intent.ACTION_SEND).also { share->
                        share.type = "text/plain"
                        share.putExtra(Intent.EXTRA_TEXT, "$SHARE$movieId")
                        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(Intent.createChooser(share, "Share with..."))
                    }

                    R.id.btn_JustWatch -> Intent(Intent.ACTION_VIEW).also { justWatch->
                        justWatch.data = Uri.parse("$JUST_WATCH$titleMovie")
                        justWatch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(justWatch)
                    }

                    R.id.btn_tmdb -> Intent(Intent.ACTION_VIEW).also { tmdb->
                        tmdb.data = Uri.parse("$TMDB_MOVIE$movieId")
                        tmdb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(tmdb)
                    }

                    R.id.btn_wiki -> Intent(Intent.ACTION_VIEW).also { wikipedia->
                        wikipedia.data = Uri.parse("$WIKIPEDIA$titleMovie")
                        wikipedia.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(wikipedia)
                    }
                }
                true
            }
        }
    }

    companion object{
        const val TITLE_MOVIE = "title_movie"
        const val MOVIE_ID = "movie_id"
        const val POSTER_PATH = "poster_path"
        const val SHARE = "https://www.themoviedb.org/movie/"
        const val JUST_WATCH = "https://www.justwatch.com/us/search?content_type=movie&q="
        const val WIKIPEDIA = "https://en.wikipedia.org/wiki/"
        const val TMDB_MOVIE = "https://www.themoviedb.org/movie/"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}