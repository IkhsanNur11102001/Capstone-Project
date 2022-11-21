package com.nur_ikhsan.themoviedb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MovieDao {

    @Insert
    suspend fun addToWatchlist(watchlistMovie: WatchlistMovie)

    @Query("SELECT * FROM watchlist")
    fun getWatchlist() : LiveData<List<WatchlistMovie>>

    @Query("SELECT count(*) FROM watchlist WHERE watchlist.id = :id")
    suspend fun checkWatchlistMovie(id : String) : Int

    @Query("DELETE FROM watchlist WHERE watchlist.id = :id")
    suspend fun deleteWatchlist(id: String) : Int

}