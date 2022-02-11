package com.example.moviedb.data.remote.model.movie.review


import com.google.gson.annotations.SerializedName

data class AuthorDetailsData(
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("rating")
    val rating: Double?
)