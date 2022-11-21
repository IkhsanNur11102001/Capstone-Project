package com.nur_ikhsan.themoviedb.ui.movie.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nur_ikhsan.themoviedb.BuildConfig.URL_THUMBNAIL
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.data.local.TrailersFavorite
import com.nur_ikhsan.themoviedb.databinding.ItemTrailersBinding

class TrailersAdapter(private val onBookmark : (TrailersFavorite) -> Unit)
    : ListAdapter<TrailersFavorite, TrailersAdapter.ViewHolder>(DIFF_CALLBACK){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrailersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trailers = getItem(position)
        holder.bind(trailers)

        val bookmark = holder.binding.imageBookmark
        if (trailers.isBookmarked){
            bookmark.setImageDrawable(ContextCompat.getDrawable(bookmark.context, R.drawable.favorite_dua))
        }else{
            bookmark.setImageDrawable(ContextCompat.getDrawable(bookmark.context, R.drawable.favorite_satu))
        }
        bookmark.setOnClickListener {
            onBookmark(trailers)
        }
    }


    class ViewHolder( val binding : ItemTrailersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trailers: TrailersFavorite?) {
            if (trailers != null){
                binding.apply {

                    itemView.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("https://www.youtube.com/watch?v=" + trailers.key)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        itemView.context.startActivity(intent)
                    }

                    Glide.with(itemView.context)
                        .load(Uri.parse(URL_THUMBNAIL + trailers.key + "/0.jpg"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(DrawableTransitionOptions.withCrossFade(100))
                        .transform(RoundedCorners(30))
                        .error(R.drawable.bg_image)
                        .into(imageTrailer)

                    tvNameTrailers.text = trailers.name
                }
            }
        }
    }

    companion object{
        val DIFF_CALLBACK : DiffUtil.ItemCallback<TrailersFavorite> =
            object : DiffUtil.ItemCallback<TrailersFavorite>(){
                override fun areItemsTheSame(
                    oldItem: TrailersFavorite,
                    newItem: TrailersFavorite
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: TrailersFavorite,
                    newItem: TrailersFavorite
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}