package com.nur_ikhsan.themoviedb.ui.movie.adapter

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
import com.nur_ikhsan.themoviedb.data.response.CollectionItem
import com.nur_ikhsan.themoviedb.databinding.ItemMovieBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailMovieActivity

class CollectionAdapter(private val collection : List<CollectionItem>) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>(){

    class ViewHolder(private val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setCollection(collectionItem: CollectionItem) {

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.MOVIE_ID, collectionItem.id)
                intent.putExtra(DetailMovieActivity.TITLE_MOVIE, collectionItem.title)
                intent.putExtra(DetailMovieActivity.POSTER_PATH, collectionItem.posterPath)
                itemView.context.startActivity(intent)
            }

            binding.apply {

                Glide.with(itemView.context)
                    .load(Uri.parse(BuildConfig.URL_IMAGE + collectionItem.posterPath))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(DrawableTransitionOptions.withCrossFade(100))
                    .transform(RoundedCorners(30))
                    .into(imageMovie)

                tvTitleMovie.text = collectionItem.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collection = collection[position]
        holder.setCollection(collection)
    }

    override fun getItemCount(): Int = collection.size
}