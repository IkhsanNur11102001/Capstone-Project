package com.nur_ikhsan.themoviedb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nur_ikhsan.themoviedb.data.network.ApiInterface
import com.nur_ikhsan.themoviedb.data.response.ResultKeyword

class PagingSourceResultKeyword(private val apiInterface: ApiInterface, private val query : String) : PagingSource<Int, ResultKeyword>() {

    companion object{
        const val FIRST_PAGE = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultKeyword> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = apiInterface.searchKeyword(query, page)
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

    override fun getRefreshKey(state: PagingState<Int, ResultKeyword>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}