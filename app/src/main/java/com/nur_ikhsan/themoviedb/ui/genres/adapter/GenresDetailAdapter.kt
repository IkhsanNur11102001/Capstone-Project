package com.nur_ikhsan.themoviedb.ui.genres.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nur_ikhsan.themoviedb.data.response.GenresMovies
import com.nur_ikhsan.themoviedb.databinding.ItemGenresDetailBinding
import com.nur_ikhsan.themoviedb.ui.activity.GenresActivity


class GenresDetailAdapter(private val genres : List<GenresMovies>) : RecyclerView.Adapter<GenresDetailAdapter.ViewHolder>(){


    inner class ViewHolder(private val binding : ItemGenresDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setGenres(genresItem: GenresMovies) {

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, GenresActivity::class.java)
                intent.putExtra(GenresActivity.GENRES_ID, genresItem.id.toString())
                intent.putExtra(GenresActivity.GENRES_NAME, genresItem.name)
                itemView.context.startActivity(intent)
            }

            binding.tvGenres.text = genresItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenresDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genresItem = genres[position]
        holder.setGenres(genresItem)
    }

    override fun getItemCount(): Int = genres.size

}

