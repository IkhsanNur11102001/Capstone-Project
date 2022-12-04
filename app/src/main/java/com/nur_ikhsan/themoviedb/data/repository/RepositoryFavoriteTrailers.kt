package com.nur_ikhsan.themoviedb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.nur_ikhsan.themoviedb.data.local.TrailersDao
import com.nur_ikhsan.themoviedb.data.local.TrailersFavorite
import com.nur_ikhsan.themoviedb.data.network.ApiInterface
import com.nur_ikhsan.themoviedb.utils.Result
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryFavoriteTrailers @Inject constructor(private val apiInterface: ApiInterface, private val trailersDao: TrailersDao) {

    fun getTrailers(movieId : String) : LiveData<Result<List<TrailersFavorite>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiInterface.getTrailers(movieId)
            val result = response.results
            val trailerList = result.map {
                val isBookmarked = trailersDao.isBookmarked(it.name)
                TrailersFavorite(
                    it.name,
                    it.id,
                    it.key,
                    isBookmarked
                )
            }
            trailersDao.deleteAll()
            trailersDao.insertTrailers(trailerList)
        }catch (e : Exception){

        }

        val localData  : LiveData<Result<List<TrailersFavorite>>> = trailersDao.addToFavorite().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getBookmarked() : LiveData<List<TrailersFavorite>>{
        return trailersDao.getFavorite()
    }

    suspend fun setBookmarkTrailers(trailersFavorite: TrailersFavorite, bookmarkState : Boolean){
        trailersFavorite.isBookmarked = bookmarkState
        trailersDao.updateTrailers(trailersFavorite)
    }
}