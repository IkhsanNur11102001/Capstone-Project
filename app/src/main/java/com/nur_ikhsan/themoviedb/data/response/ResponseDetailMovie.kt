package com.nur_ikhsan.themoviedb.data.response

import com.google.gson.annotations.SerializedName

data class ResponseDetailMovie(

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("imdb_id")
	val imdbId: String,

	@field:SerializedName("video")
	val video: Boolean,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("revenue")
	val revenue: Number,

	@field:SerializedName("genres")
	val genres: List<GenresMovies>,

	@field:SerializedName("popularity")
	val popularity: String,

	@field:SerializedName("production_countries")
	val productionCountries: List<ProductionCountriesItem>,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("vote_count")
	val voteCount: Int,

	@field:SerializedName("budget")
	val budget: Number,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_title")
	val originalTitle: String,

	@field:SerializedName("runtime")
	val runtime: Int,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItem>,

	@field:SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesItem>,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("belongs_to_collection")
	val belongsToCollection: BelongsToCollection,

	@field:SerializedName("tagline")
	val tagline: String,

	@field:SerializedName("adult")
	val adult: Boolean,

	@field:SerializedName("homepage")
	val homepage: String,

	@field:SerializedName("status")
	val status: String
)

data class ProductionCompaniesItem(

	@field:SerializedName("logo_path")
	val logoPath: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("origin_country")
	val originCountry: String
)

data class ProductionCountriesItem(

	@field:SerializedName("iso_3166_1")
	val iso31661: String,

	@field:SerializedName("name")
	val name: String
)

data class SpokenLanguagesItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("iso_639_1")
	val iso6391: String
)

data class BelongsToCollection(

	@SerializedName("id")
	val id: String,

	@SerializedName("name")
	val name: String,

	@SerializedName("poster_path")
	val posterPath: String,

	@SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("parts")
	val parts: List<CollectionItem>
)

data class CollectionItem(

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("poster_path")
	val posterPath: String
)

