package com.example.moviedb.data.remote.model.genre

import com.google.gson.annotations.SerializedName

data class GenreListResponse(
    @SerializedName("genres")
    val genres: List<GenreData>
)