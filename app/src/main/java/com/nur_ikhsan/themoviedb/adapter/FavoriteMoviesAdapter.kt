package com.nur_ikhsan.themoviedb.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.data.local.FavoriteMovie
import com.nur_ikhsan.themoviedb.databinding.ItemMovieBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailMovieActivity

class FavoriteMoviesAdapter(private val favoriteMovies : List<FavoriteMovie>) : RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>(){

    class ViewHolder(private val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setWatchlist(favoriteMovie: FavoriteMovie) {

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.MOVIE_ID, favoriteMovie.id)
                intent.putExtra(DetailMovieActivity.TITLE_MOVIE, favoriteMovie.title)
                intent.putExtra(DetailMovieActivity.POSTER_PATH, favoriteMovie.poster_path)
                itemView.context.startActivity(intent)
            }

            binding.apply {

                Glide.with(itemView.context)
                    .load(Uri.parse("https://image.tmdb.org/t/p/w342/${favoriteMovie.poster_path}"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(RoundedCorners(30))
                    .transition(DrawableTransitionOptions.withCrossFade(100))
                    .error(R.drawable.bg_image)
                    .into(imageMovie)

                tvTitleMovie.text = favoriteMovie.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = favoriteMovies[position]
        holder.setWatchlist(favorite)
    }

    override fun getItemCount(): Int = favoriteMovies.size
}