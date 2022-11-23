package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseProviders(

	@field:SerializedName("results")
	val results: List<ProvidersItem>
)

data class ProvidersItem(

	@field:SerializedName("logo_path")
	val logoPath: String,

	@field:SerializedName("provider_id")
	val providerId: String,

	@field:SerializedName("display_priority")
	val displayPriority: Int,

	@field:SerializedName("provider_name")
	val providerName: String
)
