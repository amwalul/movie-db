package com.example.moviedb.data.remote.model.movie.video


import com.google.gson.annotations.SerializedName

data class MovieVideoListResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<MovieVideoListData>
)