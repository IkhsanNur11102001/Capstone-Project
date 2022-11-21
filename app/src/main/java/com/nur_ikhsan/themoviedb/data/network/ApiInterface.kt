package com.nur_ikhsan.themoviedb.data.network

import com.nur_ikhsan.themoviedb.BuildConfig.API_KEY
import com.nur_ikhsan.themoviedb.data.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("movie/{category}?api_key=$API_KEY")
    suspend fun getAllMovies(@Path("category") category : String, @Query("page") page : Int) : Response<ResponseMovies>

    @GET("genre/movie/list?api_key=$API_KEY")
    fun getGenresMovies() : Call<ResponseGenres>

    @GET("collection/{collection_id}?api_key=$API_KEY")
    fun getDetailCollection(@Path("collection_id") collectionId : String) : Call<BelongsToCollection>

    @GET("movie/{movie_id}?api_key=$API_KEY")
    fun getDetailsMovie(@Path("movie_id") movieId : String) : Call<ResponseDetailMovie>

    @GET("discover/movie?api_key=$API_KEY")
    suspend fun getMovieByGenres(@Query("with_genres") genresId : String, @Query("page") page : Int) : Response<ResponseMovies>

    @GET("search/movie?api_key=$API_KEY")
    suspend fun searchMovie(@Query("query") query : String, @Query("page") page : Int) : Response<ResponseMovies>

    @GET("movie/{movie_id}/videos?api_key=$API_KEY")
    suspend fun getTrailers(@Path("movie_id") movieId : String) : ResponseTrailers

}