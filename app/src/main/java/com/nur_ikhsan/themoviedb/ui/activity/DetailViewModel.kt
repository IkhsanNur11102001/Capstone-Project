package com.nur_ikhsan.themoviedb.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nur_ikhsan.themoviedb.data.repository.RepositoryMovies
import com.nur_ikhsan.themoviedb.data.response.ResultMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel
@Inject constructor(private val repositoryMovies: RepositoryMovies) : ViewModel(){

    fun detailMovie(movie_id : String) = repositoryMovies.getDetailMovie(movieId = movie_id)

    fun movieCollection(movie_id: String) = repositoryMovies.getCollectionMovie(movieId = movie_id)

    fun getGenresMovie(movie_id: String) = repositoryMovies.getDetailGenreMovie(movieId = movie_id)

    fun getDetailCollection(collectionId: String) = repositoryMovies.getDetailCollection(collectionId = collectionId)

    fun getPartsOfCollection(collectionId: String) = repositoryMovies.getPartsOfCollection(collectionId = collectionId)

    fun getProvidersMovie(movie_id: String) = repositoryMovies.getProviderMovieDetail(movieId = movie_id)

    fun getReleaseItem(movie_id: String) = repositoryMovies.getReleaseMovie(movieId = movie_id)

    fun getCastMovie(movie_id: String) = repositoryMovies.getCastMovie(movieId = movie_id)

    fun getCrewMovie(movie_id: String) = repositoryMovies.getCrewMovie(movieId = movie_id)

    fun getReviewsMovie(movie_id: String) = repositoryMovies.getReviews(movieId = movie_id)

    fun getRecommendationMovie(movie_id: String) : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getRecommendation(movieId = movie_id).cachedIn(viewModelScope)

    fun getSimilar(movie_id: String) : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getSimilar(movieId = movie_id).cachedIn(viewModelScope)



}