package com.example.moviedb.data.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviedb.data.AppPagingSource
import com.example.moviedb.data.Resource
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.model.movie.list.MovieListData
import com.example.moviedb.data.remote.model.movie.review.MovieReviewListData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {
    override fun getMovieListByGenre(genreId: Int, cacheScope: CoroutineScope) =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                object : AppPagingSource<MovieListData>() {
                    override suspend fun handleLoad(page: Int): LoadResult<Int, MovieListData> {
                        return try {
                            val response = apiService.discoverMovieListByGenres(page, genreId)
                            LoadResult.Page(
                                data = response.results,
                                prevKey = null,
                                (response.page + 1).takeIf { it <= response.totalPages }
                            )
                        } catch (e: Exception) {
                            LoadResult.Error(e)
                        }
                    }
                }
            }
        ).flow.cachedIn(cacheScope)

    override fun getMovie(id: Int) = Resource.fromSourceFlow {
        apiService.getMovie(id)
    }

    override fun getMovieVideoList(id: Int) = Resource.fromSourceFlow {
        apiService.getMovieVideos(id)
    }

    override fun getMovieReviewList(id: Int, cacheScope: CoroutineScope) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            object : AppPagingSource<MovieReviewListData>() {
                override suspend fun handleLoad(page: Int): LoadResult<Int, MovieReviewListData> {
                    return try {
                        val response = apiService.getMovieReviews(id, page)
                        LoadResult.Page(
                            data = response.results,
                            prevKey = null,
                            (response.page + 1).takeIf { it <= response.totalPages }
                        )
                    } catch (e: Exception) {
                        LoadResult.Error(e)
                    }
                }
            }
        }
    ).flow.cachedIn(cacheScope)
}