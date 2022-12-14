package com.nur_ikhsan.themoviedb.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.data.response.CastItem
import com.nur_ikhsan.themoviedb.databinding.ItemCreditsBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailCreditsActivity

class CastAdapter : ListAdapter<CastItem, CastAdapter.ViewHolder>(CAST) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCreditsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val credits = getItem(position)
        holder.setCredits(credits)
    }

    class ViewHolder(val binding : ItemCreditsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setCredits(credits: CastItem?) {
            if (credits != null){

                itemView.setOnClickListener {
                    Intent(itemView.context, DetailCreditsActivity::class.java).also { intent ->
                        intent.putExtra(DetailCreditsActivity.CREDITS_ID, credits.id)
                        intent.putExtra(DetailCreditsActivity.CREDITS_NAME, credits.name)
                        itemView.context.startActivity(intent)
                    }
                }

                binding.apply {
                    imageCredits.load("https://image.tmdb.org/t/p/w185/${credits.profilePath}"){
                        crossfade(true)
                        crossfade(100)
                        transformations(CircleCropTransformation())
                        error(R.drawable.ic_profile)
                    }
                    tvNameCredits.text = credits.name
                    tvJobOrCharacter.text = credits.character
                }
            }
        }
    }

    companion object{
        val CAST : DiffUtil.ItemCallback<CastItem> =
          object : DiffUtil.ItemCallback<CastItem>(){
              override fun areItemsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
                  return oldItem.id == newItem.id
              }

              override fun areContentsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
                  return oldItem == newItem
              }
          }
    }

}
