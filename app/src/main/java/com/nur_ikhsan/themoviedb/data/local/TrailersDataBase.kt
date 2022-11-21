package com.nur_ikhsan.themoviedb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TrailersFavorite::class], version = 1, exportSchema = false)
abstract class TrailersDataBase : RoomDatabase(){
    abstract fun trailersDao() : TrailersDao

    companion object{
        @Volatile
        private var instance : TrailersDataBase? = null
        fun getInstance(context: Context): TrailersDataBase =
            instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext, TrailersDataBase::class.java, "favorite"
                ).build()
            }
    }
}