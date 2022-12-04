package com.nur_ikhsan.themoviedb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteMoviesDao {

    @Insert
    suspend fun addToFavorite(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM favoritemovies")
    fun getFavorite() : LiveData<List<FavoriteMovie>>

    @Query("SELECT count(*) FROM favoritemovies WHERE favoritemovies.id = :id")
    suspend fun checkFavoriteMovies(id : String) : Int

    @Query("DELETE FROM favoritemovies WHERE favoritemovies.id = :id")
    suspend fun deleteFavorite(id: String) : Int

}