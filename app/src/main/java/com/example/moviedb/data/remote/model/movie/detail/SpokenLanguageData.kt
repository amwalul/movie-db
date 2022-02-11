package com.example.moviedb.data.remote.model.movie.detail

import com.google.gson.annotations.SerializedName

data class SpokenLanguageData(
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String
)