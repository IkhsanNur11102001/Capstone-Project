package com.nur_ikhsan.themoviedb.ui.movie.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.nur_ikhsan.themoviedb.BuildConfig.URL_IMAGE
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.data.response.ReviewsItem
import com.nur_ikhsan.themoviedb.databinding.ItemReviewsBinding
import com.nur_ikhsan.themoviedb.utils.DateFormat

class ReviewsAdapter : ListAdapter<ReviewsItem, ReviewsAdapter.ViewHolder>(Reviews) {



    class ViewHolder (val binding : ItemReviewsBinding) : RecyclerView.ViewHolder(binding.root){
        fun setReviews(reviews: ReviewsItem?) {
            if (reviews != null){
                val avatar = reviews.authorDetails.avatarPath
                    binding.apply {
                        imageReviews.load("$URL_IMAGE${avatar}"){
                            crossfade(true)
                            crossfade(100)
                            transformations(CircleCropTransformation())
                            error(R.drawable.ic_profile)
                        }
                        tvNameReviews.text = reviews.author
                        tvRating.text = reviews.authorDetails.rating.toString()
                        tvReviews.text = reviews.content
                        tvUpdate.text = DateFormat.formatDate(reviews.updatedAt, "dd, MMMM yyyy")

                        btnOpen.setOnClickListener {
                            Intent(Intent.ACTION_VIEW).also { intent ->
                                intent.data = Uri.parse(reviews.url)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                itemView.context.startActivity(intent)
                            }
                        }
                    }
                }
            }
        }

    object Reviews : DiffUtil.ItemCallback<ReviewsItem>() {
        override fun areItemsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reviews = getItem(position)
        holder.setReviews(reviews)
    }
}