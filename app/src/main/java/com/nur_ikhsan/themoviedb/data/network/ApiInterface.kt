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
    suspend fun getAllMovies(@Path("category") category : String, @Query("page") page : Int, @Query("region") region: String) : Response<ResponseMovies>

    @GET("genre/movie/list?api_key=$API_KEY")
    fun getGenresMovies() : Call<ResponseGenres>

    @GET("collection/{collection_id}?api_key=$API_KEY")
    fun getDetailCollection(@Path("collection_id") collectionId : String) : Call<BelongsToCollection>

    @GET("movie/{movie_id}?api_key=$API_KEY")
    fun getDetailsMovie(@Path("movie_id") movieId : String) : Call<ResponseDetailMovie>

    @GET("discover/movie?api_key=$API_KEY")
    suspend fun getMovieByGenres(@Query("with_genres") genresId : String, @Query("page") page : Int,
                                 @Query("region") region: String,
                                 @Query("primary_release_year") year : Int) : Response<ResponseMovies>

    @GET("search/movie?api_key=$API_KEY")
    suspend fun searchMovie(@Query("query") query : String, @Query("page") page : Int,
                            @Query("region") region: String) : Response<ResponseMovies>

    @GET("search/keyword?api_key=$API_KEY")
    suspend fun searchKeyword(@Query("query") query : String, @Query("page") page : Int) : Response<ResponseKeyword>

    @GET("movie/{movie_id}/videos?api_key=$API_KEY")
    suspend fun getTrailers(@Path("movie_id") movieId : String) : ResponseTrailers



    @GET("watch/providers/movie?api_key=$API_KEY")
    fun getProviders(@Query("watch_region") region : String) : Call<ResponseProviders>


    @GET("discover/movie?api_key=$API_KEY")
    suspend fun getMovieByProviders(@Query("with_watch_providers") providers : String,
                                    @Query("watch_region") region: String,
                                    @Query("page") page : Int,
                                    @Query("primary_release_year") year : Int) : Response<ResponseMovies>



    @GET("discover/movie?api_key=$API_KEY")
    suspend fun getMovieWithProviders(@Query("with_watch_providers") providers : String,
                                      @Query("watch_region") region: String,
                                      @Query("page") page : Int,
                                      @Query("primary_release_year") year : Int) : Response<ResponseMovies>


    @GET("movie/{movie_id}/watch/providers?api_key=$API_KEY")
    fun getProvidersMovieDetail(@Path("movie_id") movieId: String) : Call<ResponseProvidersDetailMovies>

    @GET("movie/{movie_id}/release_dates?api_key=$API_KEY")
    fun getReleaseMovie(@Path("movie_id") movieId: String,
                        @Query("region") region: String) : Call<ResponseReleaseMovie>

    @GET("movie/{movie_id}/credits?api_key=$API_KEY")
    fun getCreditsMovie(@Path("movie_id") movieId: String) : Call<ResponseCreditsMovie>

    @GET("movie/{movie_id}/reviews?api_key=$API_KEY")
    fun getReviews(@Path("movie_id") movieId: String) : Call<ResponseReviews>


}