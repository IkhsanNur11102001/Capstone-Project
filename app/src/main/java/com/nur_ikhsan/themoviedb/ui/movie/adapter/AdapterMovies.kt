package com.nur_ikhsan.themoviedb.ui.movie.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.data.response.ResultMovie
import com.nur_ikhsan.themoviedb.databinding.ItemMovieBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailMovieActivity

class AdapterMovies : PagingDataAdapter<ResultMovie, AdapterMovies.ViewHolder>(COMPARATOR) {


    inner class ViewHolder(private val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setMovies(movies: ResultMovie) {

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.MOVIE_ID, movies.id)
                intent.putExtra(DetailMovieActivity.TITLE_MOVIE, movies.title)
                intent.putExtra(DetailMovieActivity.POSTER_PATH, movies.posterPath)
                itemView.context.startActivity(intent)
            }

            binding.apply {

                tvTitleMovie.text = movies.title
                val posterPath = movies.posterPath

                    Glide.with(itemView.context)
                        .load(Uri.parse("https://image.tmdb.org/t/p/w342/${posterPath}"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.bg_image)
                        .transition(DrawableTransitionOptions.withCrossFade(100))
                        .transform(RoundedCorners(30))
                        .into(imageMovie)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null){
            holder.setMovies(movies)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    object COMPARATOR : DiffUtil.ItemCallback<ResultMovie>() {

        override fun areItemsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean = oldItem == newItem

    }

}