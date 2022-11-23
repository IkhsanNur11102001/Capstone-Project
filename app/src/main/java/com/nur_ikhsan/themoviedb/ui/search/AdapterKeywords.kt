package com.nur_ikhsan.themoviedb.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nur_ikhsan.themoviedb.data.response.ResultKeyword
import com.nur_ikhsan.themoviedb.databinding.ItemKeywordBinding

class AdapterKeywords : PagingDataAdapter<ResultKeyword, AdapterKeywords.ViewHolder>(COMPARATOR) {


    inner class ViewHolder(private val binding : ItemKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setKeyword(keyWord: ResultKeyword) {
            binding.tvKeyword.text = keyWord.name
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keyWord = getItem(position)
        if (keyWord != null){
            holder.setKeyword(keyWord)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    object COMPARATOR : DiffUtil.ItemCallback<ResultKeyword>() {

        override fun areItemsTheSame(oldItem: ResultKeyword, newItem: ResultKeyword): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ResultKeyword, newItem: ResultKeyword): Boolean = oldItem == newItem

    }

}