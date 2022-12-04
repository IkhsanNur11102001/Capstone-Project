package com.nur_ikhsan.themoviedb.data.repository

import com.nur_ikhsan.themoviedb.data.local.WatchlistMovieDao
import com.nur_ikhsan.themoviedb.data.local.WatchlistMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryWatchlist @Inject constructor(private val watchlistMovieDao: WatchlistMovieDao) {
    suspend fun addToWatchlist(watchlistMovie: WatchlistMovie) = watchlistMovieDao.addToWatchlist(watchlistMovie)
    fun getWatchlist() = watchlistMovieDao.getWatchlist()
    suspend fun checkWatchlist(id : String) = watchlistMovieDao.checkWatchlistMovie(id)
    suspend fun removeFromWatchlist(id: String){
        watchlistMovieDao.deleteWatchlist(id)
    }
}