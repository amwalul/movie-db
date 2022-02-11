package com.example.moviedb.data.repository.genre

import com.example.moviedb.data.Resource
import com.example.moviedb.data.remote.model.genre.GenreListResponse
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenreList(): Flow<Resource<GenreListResponse>>
}