package com.nur_ikhsan.themoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [WatchlistMovie::class], version = 1)
abstract class WatchlistDatabase : RoomDatabase() {
    abstract fun getWatchlistMovie() : WatchlistMovieDao
}