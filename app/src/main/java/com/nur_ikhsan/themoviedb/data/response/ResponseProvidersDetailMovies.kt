package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseProvidersDetailMovies(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("results")
	val results: ProvidersUS
)

data class ProvidersUS(

	@field:SerializedName("US")
	val uS: US

)

data class US(

	@field:SerializedName("buy")
	val buy: List<BuyItem>,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("rent")
	val rent: List<RentItem>,

	@field:SerializedName("flatrate")
	val stream: List<Stream>
)

data class RentItem(

	@field:SerializedName("logo_path")
	val logoPath: String,

	@field:SerializedName("provider_id")
	val providerId: Int,

	@field:SerializedName("display_priority")
	val displayPriority: Int,

	@field:SerializedName("provider_name")
	val providerName: String
)

data class BuyItem(

	@field:SerializedName("logo_path")
	val logoPath: String,

	@field:SerializedName("provider_id")
	val providerId: Int,

	@field:SerializedName("display_priority")
	val displayPriority: Int,

	@field:SerializedName("provider_name")
	val providerName: String
)

data class Stream(

	@field:SerializedName("logo_path")
	val logoPath: String,

	@field:SerializedName("provider_id")
	val providerId: Int,

	@field:SerializedName("display_priority")
	val displayPriority: Int,

	@field:SerializedName("provider_name")
	val providerName: String
)
