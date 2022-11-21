package com.nur_ikhsan.themoviedb.ui.watchlist

import androidx.lifecycle.ViewModel
import com.nur_ikhsan.themoviedb.data.repository.RepositoryWatchlist
import com.nur_ikhsan.themoviedb.data.local.WatchlistMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WatchlistViewModel @Inject constructor(private val repositoryWatchlist: RepositoryWatchlist) : ViewModel() {

    fun addToWatchlist(title : String, id : String, posterPath : String){
        CoroutineScope(Dispatchers.IO).launch {
            repositoryWatchlist.addToWatchlist(
                WatchlistMovie(
                    title,
                    id,
                    posterPath
                )
            )
        }
    }

    val watchlist = repositoryWatchlist.getWatchlist()

    suspend fun checkWatchlistMovie(id : String) = repositoryWatchlist.checkWatchlist(id)


    fun removeFromWatchlist(id : String){
        CoroutineScope(Dispatchers.IO).launch {
            repositoryWatchlist.removeFromWatchlist(id)
        }
    }
}