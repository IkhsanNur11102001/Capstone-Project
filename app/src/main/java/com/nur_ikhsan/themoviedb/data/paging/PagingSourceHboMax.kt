package com.nur_ikhsan.themoviedb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nur_ikhsan.themoviedb.data.network.ApiInterface
import com.nur_ikhsan.themoviedb.data.response.ResultMovie

class PagingSourceHboMax(private val apiInterface: ApiInterface) : PagingSource<Int, ResultMovie>() {

    companion object{
        const val STARTING_PAGE = 1
        const val WATCH_REGION = "US"
        const val REGION = "US"
        const val HBO = "384"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultMovie> {
        return try {
            val page = params.key ?: STARTING_PAGE
            val response = apiInterface.getMovieWithProviders(providers = HBO, WATCH_REGION,REGION, page)
            val dataMovies = response.body()?.results

            LoadResult.Page(
                data = dataMovies!!,
                prevKey = if (page == STARTING_PAGE) null else page -1,
                nextKey = if (dataMovies.isEmpty()) null else page +1
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}