package com.nur_ikhsan.themoviedb.data.repository

import com.nur_ikhsan.themoviedb.data.local.FavoriteMovie
import com.nur_ikhsan.themoviedb.data.local.FavoriteMoviesDao
import com.nur_ikhsan.themoviedb.data.local.WatchlistMovieDao
import com.nur_ikhsan.themoviedb.data.local.WatchlistMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryFavoriteMovies @Inject constructor(private val favoriteMoviesDao: FavoriteMoviesDao) {
    suspend fun addToFavorite(favoriteMovie: FavoriteMovie) = favoriteMoviesDao.addToFavorite(favoriteMovie)
    fun getFavorite() = favoriteMoviesDao.getFavorite()
    suspend fun checkFavorite(id : String) = favoriteMoviesDao.checkFavoriteMovies(id)
    suspend fun removeFromFavorite(id: String){
        favoriteMoviesDao.deleteFavorite(id)
    }
}