package com.example.moviedb.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class AppPagingSource<T : Any> : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return handleLoad(page)
    }

    abstract suspend fun handleLoad(page: Int): LoadResult<Int, T>
}