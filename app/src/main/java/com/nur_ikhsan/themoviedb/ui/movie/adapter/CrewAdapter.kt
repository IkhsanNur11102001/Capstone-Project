package com.nur_ikhsan.themoviedb.ui.movie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.nur_ikhsan.themoviedb.BuildConfig.URL_IMAGE
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.data.response.CrewItem
import com.nur_ikhsan.themoviedb.databinding.ItemCreditsBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailCreditsActivity

class CrewAdapter : ListAdapter<CrewItem, CrewAdapter.ViewHolder>(CREW) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCreditsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crew = getItem(position)
        holder.setCrew(crew)
    }


    class ViewHolder(val binding : ItemCreditsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setCrew(crew: CrewItem?) {
            if (crew != null){

                itemView.setOnClickListener {
                    Intent(itemView.context, DetailCreditsActivity::class.java).also { intent ->
                        intent.putExtra(DetailCreditsActivity.CREDITS_ID, crew.id)
                        intent.putExtra(DetailCreditsActivity.CREDITS_NAME, crew.name)
                        itemView.context.startActivity(intent)
                    }
                }

                binding.apply {
                    imageCredits.load("https://image.tmdb.org/t/p/w185/${crew.profilePath}"){
                        crossfade(true)
                        crossfade(false)
                        transformations(CircleCropTransformation())
                        error(R.drawable.ic_profile)
                    }
                    tvNameCredits.text = crew.name
                    tvJobOrCharacter.text = "${crew.job} - ${crew.department}"
                }
            }
        }
    }


    object CREW : DiffUtil.ItemCallback<CrewItem>() {
        override fun areItemsTheSame(oldItem: CrewItem, newItem: CrewItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CrewItem, newItem: CrewItem): Boolean {
            return oldItem == newItem
        }
    }
}