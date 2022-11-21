package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseMovies(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<ResultMovie>,

	@field:SerializedName("total_results")
	val totalResults: Int
)

data class ResultMovie(

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("poster_path")
	val posterPath: String
)
