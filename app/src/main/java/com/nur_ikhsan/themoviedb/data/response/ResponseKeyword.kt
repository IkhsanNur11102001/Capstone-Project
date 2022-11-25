package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseKeyword(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<ResultKeyword>,

	@field:SerializedName("total_results")
	val totalResults: Int
)

data class ResultKeyword(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
)
