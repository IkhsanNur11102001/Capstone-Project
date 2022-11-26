package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseDetailCredits(

	@field:SerializedName("birthday")
	val birthday: String,

	@field:SerializedName("known_for_department")
	val knownForDepartment: String,

	@field:SerializedName("profile_path")
	val profilePath: String,

	@field:SerializedName("biography")
	val biography: String,

	@field:SerializedName("place_of_birth")
	val placeOfBirth: String,

	@field:SerializedName("popularity")
	val popularity: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
