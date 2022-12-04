package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseCreditsMovie(

	@field:SerializedName("cast")
	val cast: List<CastItem>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("crew")
	val crew: List<CrewItem>
)

data class CastItem(

	@field:SerializedName("character")
	val character: String,

	@field:SerializedName("original_name")
	val originalName: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("profile_path")
	val profilePath: String,

	@field:SerializedName("id")
	val id: String,
)

data class CrewItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("profile_path")
	val profilePath: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("department")
	val department: String,

	@field:SerializedName("job")
	val job: String
)
