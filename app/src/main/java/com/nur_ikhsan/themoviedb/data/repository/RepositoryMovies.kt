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

    fun getResultKeyword(query : String) =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {PagingSourceResultKeyword(apiInterface, query = query)}
        ).liveData

    fun getMovieByProviders(providersId : String) =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {PagingSourceProviders(apiInterface = apiInterface, providersId)}
        ).liveData


    fun getNetflixMovies() =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {PagingSourceNetflix(apiInterface = apiInterface)}
        ).liveData


    fun getAppleMovies() =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {PagingSourceApple(apiInterface = apiInterface)}
        ).liveData


    fun getDisneyMovies() =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {PagingSourceDisney(apiInterface = apiInterface)}
        ).liveData

    fun getHboMax() =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {PagingSourceHboMax(apiInterface)}
    ).liveData


    fun getProviderMovieDetail(movieId: String) : LiveData<ProvidersUS>{
        val resultProviders = MutableLiveData<ProvidersUS>()
        val response = apiInterface.getProvidersMovieDetail(movieId)
        response.enqueue(object : Callback<ResponseProvidersDetailMovies>{
            override fun onResponse(
                call: Call<ResponseProvidersDetailMovies>,
                response: Response<ResponseProvidersDetailMovies>
            ) {
                if (response.isSuccessful){
                    resultProviders.value = response.body()?.results
                }
            }

            override fun onFailure(call: Call<ResponseProvidersDetailMovies>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return resultProviders
    }

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

    fun getProviders() : LiveData<List<ProvidersItem>>{
        val resultProviders = MutableLiveData<List<ProvidersItem>>()
        val response = apiInterface.getProviders(REGION)

        response.enqueue(object : Callback<ResponseProviders>{
            override fun onResponse(
                call: Call<ResponseProviders>,
                response: Response<ResponseProviders>
            ) {
                if (response.isSuccessful){
                    resultProviders.value = response.body()?.results
                }
            }

            override fun onFailure(call: Call<ResponseProviders>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return resultProviders
    }

    companion object{
        const val REGION = "US"
    }

}