package com.nur_ikhsan.themoviedb.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nur_ikhsan.themoviedb.data.local.TrailersFavorite
import com.nur_ikhsan.themoviedb.data.repository.RepositoryFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repositoryFavorite: RepositoryFavorite) : ViewModel() {

    fun getTrailers(movieId : String) = repositoryFavorite.getTrailers(movieId)

    fun getBookmarkState() = repositoryFavorite.getBookmarked()

    fun saveFavorite(trailersFavorite: TrailersFavorite){
        viewModelScope.launch {
            repositoryFavorite.setBookmarkTrailers(trailersFavorite, true)
        }
    }

    fun deleteFavorite(trailersFavorite: TrailersFavorite){
        viewModelScope.launch {
            repositoryFavorite.setBookmarkTrailers(trailersFavorite, false)
        }
    }
}