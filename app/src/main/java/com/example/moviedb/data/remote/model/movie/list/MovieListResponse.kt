package com.example.moviedb.data.remote.model.movie.list

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieListData>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)