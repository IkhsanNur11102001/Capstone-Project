package com.nur_ikhsan.themoviedb.data.di

import android.content.Context
import androidx.room.Room
import com.nur_ikhsan.themoviedb.BuildConfig.BASE_URL
import com.nur_ikhsan.themoviedb.data.local.TrailersDataBase
import com.nur_ikhsan.themoviedb.data.local.WatchlistDatabase
import com.nur_ikhsan.themoviedb.data.network.ApiInterface
import com.nur_ikhsan.themoviedb.data.repository.RepositoryFavorite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesTrailersDatabase(@ApplicationContext app : Context) = Room.databaseBuilder(
        app, TrailersDataBase::class.java, "favorite").build()

    @Singleton
    @Provides
    fun providesTrailersDao(db : TrailersDataBase) = db.trailersDao()

    @Singleton
    @Provides
    fun providesMovieDatabase(@ApplicationContext app : Context) = Room.databaseBuilder(
        app, WatchlistDatabase::class.java, "watchlist").build()

    @Singleton
    @Provides
    fun providesWatchlistDao(db : WatchlistDatabase) = db.getWatchlistMovie()

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)
}