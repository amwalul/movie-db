package com.example.moviedb.data.remote.model.movie.review


import com.google.gson.annotations.SerializedName

data class MovieReviewListResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieReviewListData>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)