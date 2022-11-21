package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseTrailers(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("results")
	val results: List<TrailersItem>
)

data class TrailersItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("key")
	val key: String
)
