package com.nur_ikhsan.themoviedb.ui.providers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.nur_ikhsan.themoviedb.BuildConfig.URL_IMAGE
import com.nur_ikhsan.themoviedb.data.response.ProvidersUS
import com.nur_ikhsan.themoviedb.data.response.Stream
import com.nur_ikhsan.themoviedb.databinding.ItemProvidersDetailBinding

class ProvidersAdapterStream(private val providers : ProvidersUS) : RecyclerView.Adapter<ProvidersAdapterStream.ViewHolder>() {

    private lateinit var onSelectData : OnSelectData

    fun onSelectData(onSelectData : OnSelectData){
        this.onSelectData = onSelectData
    }

    interface OnSelectData {
        fun itemClicked(link : String)
    }

    inner class ViewHolder(private val binding : ItemProvidersDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setProviders(provider: Stream) {

            itemView.setOnClickListener {
                onSelectData.itemClicked(link = providers.uS.link)
            }

            val logo = provider.logoPath
            val name = provider.providerName
            if (logo.isNotEmpty() && name.isNotEmpty()){
                binding.apply {
                    imageProviders.load("$URL_IMAGE$logo"){
                        crossfade(100)
                        crossfade(true)
                        transformations(RoundedCornersTransformation(30f))
                    }
                    tvProviders.text = name
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProvidersDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provider = providers.uS.stream[position]
        holder.setProviders(provider)
    }

    override fun getItemCount(): Int = providers.uS.stream.size
}