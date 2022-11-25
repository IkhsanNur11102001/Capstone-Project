package com.nur_ikhsan.themoviedb.ui.watchlist

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nur_ikhsan.themoviedb.BuildConfig
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.data.local.WatchlistMovie
import com.nur_ikhsan.themoviedb.databinding.ItemMovieBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailMovieActivity

class WatchlistAdapter(private val watchlistMovie : List<WatchlistMovie>) : RecyclerView.Adapter<WatchlistAdapter.ViewHolder>(){

    class ViewHolder(private val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setWatchlist(watchlist: WatchlistMovie) {

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.MOVIE_ID, watchlist.id)
                intent.putExtra(DetailMovieActivity.TITLE_MOVIE, watchlist.title)
                intent.putExtra(DetailMovieActivity.POSTER_PATH, watchlist.poster_path)
                itemView.context.startActivity(intent)
            }

            binding.apply {

                Glide.with(itemView.context)
                    .load(Uri.parse("https://image.tmdb.org/t/p/w500/${watchlist.poster_path}"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(RoundedCorners(30))
                    .transition(DrawableTransitionOptions.withCrossFade(100))
                    .error(R.drawable.bg_image)
                    .into(imageMovie)

                tvTitleMovie.text = watchlist.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val watchlist = watchlistMovie[position]
        holder.setWatchlist(watchlist)
    }

    override fun getItemCount(): Int = watchlistMovie.size
}