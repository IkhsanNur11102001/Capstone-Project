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
class RepositoryMovies @Inject constructor(private val apiInterface: ApiInterface) {

    fun getToRatedMovies() = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = { PagingSourceTopRated(apiInterface = apiInterface) }
    ).liveData


    fun getPopularMovies() = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = { PagingSourcePopular(apiInterface = apiInterface) }
    ).liveData


    fun getUpComingMovies() = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = { PagingSourceUpComing(apiInterface = apiInterface) }
    ).liveData


    fun getNowPlayingMovies() = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = { PagingSourceNowPlaying(apiInterface = apiInterface) }
    ).liveData


    fun getMoviesByGenres(genresId : String) =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = {PagingSourceMovieGenres(apiInterface, genresId = genresId)}
        ).liveData

    fun getResultMovies(query : String) =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = {PagingSourceResultMovie(apiInterface, query = query)}
        ).liveData

    fun getResultKeyword(query : String) =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = {PagingSourceResultKeyword(apiInterface, query = query)}
        ).liveData

    fun getNetflixMovies() =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = {PagingSourceNetflix(apiInterface = apiInterface)}
        ).liveData


    fun getAppleMovies() =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = {PagingSourceApple(apiInterface = apiInterface)}
        ).liveData


    fun getDisneyMovies() =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = {PagingSourceDisney(apiInterface = apiInterface)}
        ).liveData

    fun getHboMax() =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = {PagingSourceHboMax(apiInterface)}
    ).liveData

    fun getParamount() =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = {PagingSourceParamount(apiInterface)}
        ).liveData

    fun getMovieByKeyword(keywordId : String) =
        Pager(config = PagingConfig(
            pageSize = 5,
            maxSize = 20
        ), pagingSourceFactory = {PagingSourceMovieByKeyword(apiInterface, keywordId = keywordId)}
    ).liveData

    fun getRecommendation(movieId: String) =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
        ), pagingSourceFactory = {PagingRecommendation(apiInterface, movieId = movieId)}
        ).liveData

    fun getSimilar(movieId: String) =
        Pager( config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
        ), pagingSourceFactory = {PagingSimilar(apiInterface, movieId = movieId)}
        ).liveData


    fun getDetailCredits(creditsId : String) : LiveData<ResponseDetailCredits>{
        val credits = MutableLiveData<ResponseDetailCredits>()
        val response = apiInterface.getDetailCredits(personId = creditsId)
        response.enqueue(object : Callback<ResponseDetailCredits>{
            override fun onResponse(
                call: Call<ResponseDetailCredits>,
                response: Response<ResponseDetailCredits>
            ) {
                if (response.isSuccessful){
                    credits.value = response.body()
                }
            }

            override fun onFailure(call: Call<ResponseDetailCredits>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return credits
    }


    fun getReviews(movieId: String) : LiveData<List<ReviewsItem>>{
        val reviews = MutableLiveData<List<ReviewsItem>>()
        val response = apiInterface.getReviews(movieId = movieId)
        response.enqueue(object : Callback<ResponseReviews>{
            override fun onResponse(
                call: Call<ResponseReviews>,
                response: Response<ResponseReviews>
            ) {
                if (response.isSuccessful){
                    reviews.value = response.body()?.results
                }
            }

            override fun onFailure(call: Call<ResponseReviews>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return reviews
    }


    fun getCastMovie(movieId: String) : LiveData<List<CastItem>>{
        val cast = MutableLiveData<List<CastItem>>()
        val response = apiInterface.getCreditsMovie(movieId)
        response.enqueue(object : Callback<ResponseCreditsMovie>{
            override fun onResponse(
                call: Call<ResponseCreditsMovie>,
                response: Response<ResponseCreditsMovie>
            ) {
                if (response.isSuccessful){
                    cast.value = response.body()?.cast
                }
            }

            override fun onFailure(call: Call<ResponseCreditsMovie>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return cast
    }


    fun getCrewMovie(movieId: String) : LiveData<List<CrewItem>>{
        val crew = MutableLiveData<List<CrewItem>>()
        val response = apiInterface.getCreditsMovie(movieId)
        response.enqueue(object : Callback<ResponseCreditsMovie>{
            override fun onResponse(
                call: Call<ResponseCreditsMovie>,
                response: Response<ResponseCreditsMovie>
            ) {
                if (response.isSuccessful){
                    crew.value = response.body()?.crew
                }
            }

            override fun onFailure(call: Call<ResponseCreditsMovie>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return crew
    }


    fun getReleaseMovie(movieId: String) : LiveData<List<ReleaseItem>>{
        val release = MutableLiveData<List<ReleaseItem>>()
        val response = apiInterface.getReleaseMovie(movieId = movieId)
        response.enqueue(object : Callback<ResponseReleaseMovie>{
            override fun onResponse(
                call: Call<ResponseReleaseMovie>,
                response: Response<ResponseReleaseMovie>
            ) {
                if (response.isSuccessful){
                    release.value = response.body()?.results
                }
            }

            override fun onFailure(call: Call<ResponseReleaseMovie>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
        return release
    }


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
}