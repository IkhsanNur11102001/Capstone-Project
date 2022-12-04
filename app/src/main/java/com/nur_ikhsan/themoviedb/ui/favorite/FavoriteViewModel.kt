package com.nur_ikhsan.themoviedb.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nur_ikhsan.themoviedb.data.local.FavoriteMovie
import com.nur_ikhsan.themoviedb.data.local.TrailersFavorite
import com.nur_ikhsan.themoviedb.data.repository.RepositoryFavoriteMovies
import com.nur_ikhsan.themoviedb.data.repository.RepositoryFavoriteTrailers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repositoryFavoriteTrailers: RepositoryFavoriteTrailers, private val repositoryFavoriteMovies: RepositoryFavoriteMovies) : ViewModel() {

    fun getTrailers(movieId : String) = repositoryFavoriteTrailers.getTrailers(movieId)

    fun getBookmarkState() = repositoryFavoriteTrailers.getBookmarked()

    fun saveFavorite(trailersFavorite: TrailersFavorite){
        viewModelScope.launch {
            repositoryFavoriteTrailers.setBookmarkTrailers(trailersFavorite, true)
        }
    }

    fun deleteFavorite(trailersFavorite: TrailersFavorite){
        viewModelScope.launch {
            repositoryFavoriteTrailers.setBookmarkTrailers(trailersFavorite, false)
        }
    }

    fun addToFavorite(title : String, id : String, posterPath : String){
        CoroutineScope(Dispatchers.IO).launch {
            repositoryFavoriteMovies.addToFavorite(FavoriteMovie(title, id, posterPath))
        }
    }

    val favorites = repositoryFavoriteMovies.getFavorite()

    suspend fun checkFavorite(id : String) = repositoryFavoriteMovies.checkFavorite(id)

    fun removeFromFavorite(id : String){
        CoroutineScope(Dispatchers.IO).launch {
            repositoryFavoriteMovies.removeFromFavorite(id)
        }
    }
}