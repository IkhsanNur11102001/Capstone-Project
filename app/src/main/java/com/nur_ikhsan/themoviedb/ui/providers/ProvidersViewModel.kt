package com.nur_ikhsan.themoviedb.ui.providers

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
class ProvidersViewModel @Inject constructor(private val repositoryMovies: RepositoryMovies) : ViewModel() {

    val providers = repositoryMovies.getProviders()

    fun getMovieByProviders(providerId : String) : LiveData<PagingData<ResultMovie>> =
        repositoryMovies.getMovieByProviders(providersId = providerId).cachedIn(viewModelScope)
}