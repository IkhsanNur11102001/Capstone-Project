package com.nur_ikhsan.themoviedb.data.repository

import com.nur_ikhsan.themoviedb.data.local.MovieDao
import com.nur_ikhsan.themoviedb.data.local.WatchlistMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryWatchlist @Inject constructor(private val movieDao: MovieDao) {
    suspend fun addToWatchlist(watchlistMovie: WatchlistMovie) = movieDao.addToWatchlist(watchlistMovie)
    fun getWatchlist() = movieDao.getWatchlist()
    suspend fun checkWatchlist(id : String) = movieDao.checkWatchlistMovie(id)
    suspend fun removeFromWatchlist(id: String){
        movieDao.deleteWatchlist(id)
    }
}