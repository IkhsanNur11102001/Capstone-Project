package com.nur_ikhsan.themoviedb.ui.search

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
class SearchViewModel @Inject constructor(private val repositoryMovies: RepositoryMovies) : ViewModel() {

    fun getResultMovies(query : String) : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getResultMovies(query = query).cachedIn(viewModelScope)

}