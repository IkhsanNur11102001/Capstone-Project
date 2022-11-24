package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseReleaseMovie(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("results")
	val results: List<ReleaseItem>
)

data class ReleaseDatesItem(

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("type")
	val type: Int,

	@field:SerializedName("iso_639_1")
	val iso6391: String,

	@field:SerializedName("certification")
	val certification: String,

	@field:SerializedName("note")
	val note: String
)

data class ReleaseItem(

	@field:SerializedName("release_dates")
	val releaseDates: List<ReleaseDatesItem>,

	@field:SerializedName("iso_3166_1")
	val iso31661: String
)
