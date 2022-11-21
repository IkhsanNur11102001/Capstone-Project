package com.nur_ikhsan.themoviedb.data.local

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TrailersDao {

    @Query("SELECT * FROM favorite ORDER BY name DESC")
    fun addToFavorite() : LiveData<List<TrailersFavorite>>

    @Query("SELECT * FROM favorite where bookmarked = 1")
    fun getFavorite() : LiveData<List<TrailersFavorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrailers(favorite : List<TrailersFavorite>)

    @Update
    suspend fun updateTrailers(trailers : TrailersFavorite)

    @Query("DELETE FROM favorite WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE name = :name AND bookmarked = 1)")
    suspend fun isBookmarked(name : String) : Boolean
}