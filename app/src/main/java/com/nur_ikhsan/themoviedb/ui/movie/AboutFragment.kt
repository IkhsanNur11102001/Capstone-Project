package com.nur_ikhsan.themoviedb.ui.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nur_ikhsan.themoviedb.BuildConfig
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.data.repository.Result
import com.nur_ikhsan.themoviedb.databinding.FragmentAboutBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailCollectionActivity
import com.nur_ikhsan.themoviedb.ui.activity.DetailMovieActivity
import com.nur_ikhsan.themoviedb.ui.activity.DetailViewModel
import com.nur_ikhsan.themoviedb.ui.favorite.FavoriteViewModel
import com.nur_ikhsan.themoviedb.ui.genres.adapter.GenresDetailAdapter
import com.nur_ikhsan.themoviedb.ui.movie.adapter.TrailersAdapter
import com.nur_ikhsan.themoviedb.utils.DateFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*


@AndroidEntryPoint
class AboutFragment : Fragment() {

    private var _binding : FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val extras = activity?.intent?.extras
        if (extras != null){
            val movieId = extras.getString(DetailMovieActivity.MOVIE_ID)
            if (movieId != null){
                initDetailMovie(movieId)
                initGenresMovie(movieId)
                initCollection(movieId)
                initTrailers(movieId)
            }
        }
    }

    private fun initTrailers(movieId: String) {

            val trailersAdapter = TrailersAdapter {
                if (it.isBookmarked) {
                    favoriteViewModel.deleteFavorite(it)
                } else {
                    favoriteViewModel.saveFavorite(it)
                }
            }

                favoriteViewModel.getTrailers(movieId = movieId).observe(viewLifecycleOwner) { trailers ->
                    if (trailers != null){
                        if (trailers is Result.Success){
                            val dataTrailers = trailers.data
                            trailersAdapter.submitList(dataTrailers)
                        }
                    }
                }

        binding.tvTrailers.text = resources.getString(R.string.trailers)
        binding.rvTrailers.adapter = trailersAdapter
        binding.rvTrailers.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
    }


    private fun initCollection(movieId: String) {
        viewModel.movieCollection(movieId).observe(viewLifecycleOwner){ collection->
            if (collection != null){
                binding.apply {
                    Glide.with(context?.applicationContext!!)
                        .load(Uri.parse(BuildConfig.URL_IMAGE + collection.backdropPath))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.bg_image)
                        .transition(DrawableTransitionOptions.withCrossFade(100))
                        .transform(RoundedCorners(30))
                        .into(imageCollection)
                    tvCollection.text = collection.name

                    imageCollection.setOnClickListener {
                        val intent = Intent(context, DetailCollectionActivity::class.java)
                        intent.putExtra(DetailCollectionActivity.COLLECTION_ID, collection.id)
                        startActivity(intent)
                    }
                }
            }else{
                binding.itemCollection.isVisible = false
            }
        }
    }

    private fun initGenresMovie(movieId: String) {
        viewModel.getGenresMovie(movieId).observe(viewLifecycleOwner){ genres->
            if (genres!!.isNotEmpty()){
                val adapter = GenresDetailAdapter(genres)
                binding.apply {
                    rvGenres.adapter = adapter
                    rvGenres.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    tvGenres.text = resources.getString(R.string.genres)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailMovie(movieId: String) {
        viewModel.detailMovie(movieId).observe(viewLifecycleOwner){ detail->
            if (detail != null){
                binding.apply {

                    Glide.with(context?.applicationContext!!)
                        .load(Uri.parse(BuildConfig.URL_IMAGE + detail.posterPath))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(DrawableTransitionOptions.withCrossFade(100))
                        .transform(RoundedCorners(30))
                        .into(imageMovie)

                    tvTitleMovie.text = detail.originalTitle
                    tvTitleMovie.isSelected = true
                    tvVoteAverage.text = detail.voteAverage.toString()
                    tvOverview.text = detail.overview
                    tvReleaseDate.text = DateFormat.formatDate(detail.releaseDate, "dd, MMMM yyyy")

                    tvMoreInformation.text = resources.getString(R.string.more_information)
                    tvLanguage.text = detail.originalLanguage

                    val language = Locale(detail.originalLanguage)
                    language.displayLanguage


                    tvLanguage.text = language.displayLanguage
                    tvStatus.text = detail.status
                    tvRuntime.text = "${detail.runtime / 60} hrs" + " ${detail.runtime % 60} mins"
                    tvCountries.text = detail.productionCountries.joinToString { it.name }

                    val format : NumberFormat = NumberFormat.getCurrencyInstance()
                    format.maximumFractionDigits = 0
                    format.currency = Currency.getInstance("USD")
                    format.format(1000000)
                    tvBudget.text = format.format(detail.budget)
                    tvRevenue.text = format.format(detail.revenue)
                    tvCompanies.text = detail.productionCompanies.joinToString { it.name }
                }
            }
        }
    }
}