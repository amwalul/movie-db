package com.example.moviedb.data.remote.model.movie.detail

import com.google.gson.annotations.SerializedName

data class ProductionCountryData(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)