package com.nur_ikhsan.themoviedb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nur_ikhsan.themoviedb.data.network.ApiInterface
import com.nur_ikhsan.themoviedb.data.response.ResultMovie

class PagingSourceApple(private val apiInterface: ApiInterface) : PagingSource<Int, ResultMovie>(){

    companion object{
        const val STARTING_PAGE = 1
        const val REGION = "US"
        const val WATCH_REGION = "US"
        const val APPLE_PLUS = "350"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultMovie> {
        return try {
            val page = params.key?: STARTING_PAGE
            val response = apiInterface.getMovieWithProviders(providers = APPLE_PLUS, WATCH_REGION, REGION, page)
            val resultMovie = response.body()?.results

            LoadResult.Page(
                data = resultMovie!!,
                prevKey = if (page == STARTING_PAGE) null else page -1,
                nextKey = if (resultMovie.isEmpty()) null else page +1
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