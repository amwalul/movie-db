package com.example.moviedb.data.remote

import com.example.moviedb.data.remote.model.genre.GenreListResponse
import com.example.moviedb.data.remote.model.movie.detail.MovieResponse
import com.example.moviedb.data.remote.model.movie.list.MovieListResponse
import com.example.moviedb.data.remote.model.movie.review.MovieReviewListResponse
import com.example.moviedb.data.remote.model.movie.video.MovieVideoListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreListResponse

    @GET("discover/movie")
    suspend fun discoverMovieListByGenres(
        @Query("page") page: Int,
        @Query("with_genres") vararg genreIds: Int
    ): MovieListResponse

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): MovieResponse

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") id: Int
    ): MovieVideoListResponse

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): MovieReviewListResponse
}