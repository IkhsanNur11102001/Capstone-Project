package com.nur_ikhsan.themoviedb.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nur_ikhsan.themoviedb.data.repository.RepositoryMovies
import com.nur_ikhsan.themoviedb.data.response.BelongsToCollection
import com.nur_ikhsan.themoviedb.data.response.ResultMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(private val repositoryMovies: RepositoryMovies) : ViewModel(){

    val topRated : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getToRatedMovies().cachedIn(viewModelScope)

    val popular : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getPopularMovies().cachedIn(viewModelScope)

    val upComing : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getUpComingMovies().cachedIn(viewModelScope)

    val nowPlaying : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getNowPlayingMovies().cachedIn(viewModelScope)

    val netflixMovie : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getNetflixMovies().cachedIn(viewModelScope)

    val appleMovies : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getAppleMovies().cachedIn(viewModelScope)

    val disneyMovies : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getDisneyMovies().cachedIn(viewModelScope)

    val hboMax : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getHboMax().cachedIn(viewModelScope)

}