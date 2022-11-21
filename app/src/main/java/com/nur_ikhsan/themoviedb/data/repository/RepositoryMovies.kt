package com.nur_ikhsan.themoviedb.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.nur_ikhsan.themoviedb.data.network.ApiInterface
import com.nur_ikhsan.themoviedb.data.paging.*
import com.nur_ikhsan.themoviedb.data.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryMovies
@Inject
constructor(private val apiInterface: ApiInterface) {

    fun getToRatedMovies() = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = { PagingSourceTopRated(apiInterface = apiInterface) }
    ).liveData


    fun getPopularMovies() = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = { PagingSourcePopular(apiInterface = apiInterface) }
    ).liveData


    fun getUpComingMovies() = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = { PagingSourceUpComing(apiInterface = apiInterface) }
    ).liveData


    fun getNowPlayingMovies() = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = { PagingSourceNowPlaying(apiInterface = apiInterface) }
    ).liveData


    fun getMoviesByGenres(genresId : String) =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {PagingSourceMovieGenres(apiInterface, genresId = genresId)}
        ).liveData

    fun getResultMovies(query : String) =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {PagingSourceResultMovie(apiInterface, query = query)}
        ).liveData


    fun getDetailCollection(collectionId : String) : LiveData<BelongsToCollection>{
        val resultCollection = MutableLiveData<BelongsToCollection>()
        val response = apiInterface.getDetailCollection(collectionId = collectionId)
        response.enqueue(object : Callback<BelongsToCollection>{
            override fun onResponse(
                call: Call<BelongsToCollection>,
                response: Response<BelongsToCollection>
            ) {
                if (response.isSuccessful){
                    resultCollection.value = response.body()
                }
            }

            override fun onFailure(call: Call<BelongsToCollection>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return resultCollection
    }

    fun getPartsOfCollection(collectionId : String) : LiveData<List<CollectionItem>>{
        val resultCollection = MutableLiveData<List<CollectionItem>>()
        val response = apiInterface.getDetailCollection(collectionId = collectionId)
        response.enqueue(object : Callback<BelongsToCollection>{
            override fun onResponse(
                call: Call<BelongsToCollection>,
                response: Response<BelongsToCollection>
            ) {
                if (response.isSuccessful){
                    resultCollection.value = response.body()?.parts
                }
            }

            override fun onFailure(call: Call<BelongsToCollection>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return resultCollection
    }


    fun getDetailMovie(movieId : String) : LiveData<ResponseDetailMovie> {
        val resultDetail = MutableLiveData<ResponseDetailMovie>()
        val response = apiInterface.getDetailsMovie(movieId = movieId)

        response.enqueue(object : Callback<ResponseDetailMovie> {
            override fun onResponse(
                call: Call<ResponseDetailMovie>,
                response: Response<ResponseDetailMovie>,
            ) {
                if (response.isSuccessful){
                    resultDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<ResponseDetailMovie>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return resultDetail
    }



    fun getCollectionMovie(movieId: String) : LiveData<BelongsToCollection>{
        val resultCollection = MutableLiveData<BelongsToCollection>()
        val response = apiInterface.getDetailsMovie(movieId = movieId)

        response.enqueue(object : Callback<ResponseDetailMovie>{
            override fun onResponse(
                call: Call<ResponseDetailMovie>,
                response: Response<ResponseDetailMovie>
            ) {
                if (response.isSuccessful){
                    resultCollection.value = response.body()?.belongsToCollection
                }
            }

            override fun onFailure(call: Call<ResponseDetailMovie>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return resultCollection
    }



    fun getDetailGenreMovie(movieId: String) : LiveData<List<GenresMovies>>{
        val resultGenres = MutableLiveData<List<GenresMovies>>()
        val response = apiInterface.getDetailsMovie(movieId = movieId)

        response.enqueue(object : Callback<ResponseDetailMovie>{
            override fun onResponse(
                call: Call<ResponseDetailMovie>,
                response: Response<ResponseDetailMovie>
            ) {
                if (response.isSuccessful){
                    resultGenres.value = response.body()?.genres
                }
            }

            override fun onFailure(call: Call<ResponseDetailMovie>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return resultGenres
    }



    fun getGenres() : LiveData<List<GenresMovies>> {
        val resultGenres = MutableLiveData<List<GenresMovies>>()
        val response = apiInterface.getGenresMovies()

        response.enqueue(object : Callback<ResponseGenres> {
            override fun onResponse(
                call: Call<ResponseGenres>,
                response: Response<ResponseGenres>,
            ) {
                if (response.isSuccessful){
                    resultGenres.value = response.body()?.genres
                }
            }

            override fun onFailure(call: Call<ResponseGenres>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return resultGenres
    }

}