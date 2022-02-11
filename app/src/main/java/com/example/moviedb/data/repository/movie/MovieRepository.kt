package com.example.moviedb.data.repository.movie

import androidx.paging.PagingData
import com.example.moviedb.data.Resource
import com.example.moviedb.data.remote.model.movie.detail.MovieResponse
import com.example.moviedb.data.remote.model.movie.list.MovieListData
import com.example.moviedb.data.remote.model.movie.review.MovieReviewListData
import com.example.moviedb.data.remote.model.movie.video.MovieVideoListResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieListByGenre(
        genreId: Int,
        cacheScope: CoroutineScope
    ): Flow<PagingData<MovieListData>>

    fun getMovie(id: Int): Flow<Resource<MovieResponse>>

    fun getMovieVideoList(id: Int): Flow<Resource<MovieVideoListResponse>>

    fun getMovieReviewList(
        id: Int,
        cacheScope: CoroutineScope
    ): Flow<PagingData<MovieReviewListData>>
}