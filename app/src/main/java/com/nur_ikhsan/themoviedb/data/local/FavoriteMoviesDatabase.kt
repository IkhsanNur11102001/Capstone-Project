package com.nur_ikhsan.themoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMoviesDatabase : RoomDatabase() {
    abstract fun getFavoriteMovies() : FavoriteMoviesDao
}