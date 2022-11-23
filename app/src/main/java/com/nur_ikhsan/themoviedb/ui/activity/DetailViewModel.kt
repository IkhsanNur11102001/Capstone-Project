package com.nur_ikhsan.themoviedb.ui.activity

import androidx.lifecycle.ViewModel
import com.nur_ikhsan.themoviedb.data.repository.RepositoryMovies
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



}