package com.nur_ikhsan.themoviedb.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.nur_ikhsan.themoviedb.data.response.ReleaseDatesItem
import com.nur_ikhsan.themoviedb.data.response.ReleaseItem
import com.nur_ikhsan.themoviedb.databinding.ItemReleaseBinding
import com.nur_ikhsan.themoviedb.utils.DateFormat
import java.util.*

class AdapterReleaseMovie(private val certificate : List<ReleaseItem>) : RecyclerView.Adapter<AdapterReleaseMovie.ViewHolder>(){

    class ViewHolder(private val binding : ItemReleaseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setCertificate(releaseItem: ReleaseItem) {
            val certificate = releaseItem.releaseDates.firstOrNull()?.certification
            val country = releaseItem.iso31661
            val locale = Locale(country, country)
            val release = releaseItem.releaseDates.firstOrNull()?.releaseDate

            binding.tvReleaseDate.text = DateFormat.formatDate(release!!, "dd, MMMM yyyy")
            binding.tvCountry.text = locale.displayCountry
            if (certificate!!.isNotEmpty()){
                binding.tvCertificate.text = certificate
                binding.tvCertificate.isVisible = true
            }else{
                binding.tvCertificate.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReleaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setCertificate(certificate[position])
    }

    override fun getItemCount(): Int = certificate.size
}