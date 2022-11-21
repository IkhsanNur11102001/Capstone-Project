package com.nur_ikhsan.themoviedb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nur_ikhsan.themoviedb.data.network.ApiInterface
import com.nur_ikhsan.themoviedb.data.response.ResultMovie

class PagingSourcePopular(private val apiInterface: ApiInterface) : PagingSource<Int, ResultMovie>() {

    companion object{
        const val FIRST_PAGE = 1
        const val POPULAR = "popular"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultMovie> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = apiInterface.getAllMovies(POPULAR, page)
            val dataMovies = response.body()?.results

            LoadResult.Page(
                data = dataMovies!!,
                prevKey = if (page == FIRST_PAGE) null else page -1,
                nextKey = if (dataMovies.isEmpty()) null else page +1
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}