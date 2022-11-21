package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseGenres(

	@field:SerializedName("genres")
	val genres: List<GenresMovies>
)

data class GenresMovies(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
