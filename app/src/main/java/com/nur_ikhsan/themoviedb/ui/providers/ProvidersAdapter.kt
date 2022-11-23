package com.nur_ikhsan.themoviedb.ui.providers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.nur_ikhsan.themoviedb.BuildConfig.URL_IMAGE
import com.nur_ikhsan.themoviedb.data.response.ProvidersItem
import com.nur_ikhsan.themoviedb.databinding.ItemProvidersBinding

class ProvidersAdapter(private val providers : List<ProvidersItem>) : RecyclerView.Adapter<ProvidersAdapter.ViewHolder>() {

    private lateinit var onSelectData : OnSelectData

    fun onSelectData(onSelectData : OnSelectData){
        this.onSelectData = onSelectData
    }

    interface OnSelectData {
        fun itemSelected(providersId : String, name : String)
    }

    inner class ViewHolder(private val binding : ItemProvidersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setProviders(provider: ProvidersItem) {

            itemView.setOnClickListener {
                onSelectData.itemSelected(providersId = provider.providerId, name = provider.providerName)
            }

            val imageProviders = provider.logoPath
            if (imageProviders.isNotEmpty()){
                binding.imageProviders.load("$URL_IMAGE$imageProviders"){
                    crossfade(100)
                    crossfade(true)
                    transformations(RoundedCornersTransformation(20f))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProvidersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provider = providers[position]
        holder.setProviders(provider)
    }

    override fun getItemCount(): Int = providers.size
}