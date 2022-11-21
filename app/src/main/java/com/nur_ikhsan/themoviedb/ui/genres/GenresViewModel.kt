package com.nur_ikhsan.themoviedb.ui.genres


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
class GenresViewModel
@Inject constructor(private val repositoryMovies: RepositoryMovies) : ViewModel(){

    val genres = repositoryMovies.getGenres()

    fun movieByGenres(genresId : String) : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getMoviesByGenres(genresId = genresId).cachedIn(viewModelScope)

}