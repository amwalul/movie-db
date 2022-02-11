package com.example.moviedb.data.repository.genre

import com.example.moviedb.data.Resource
import com.example.moviedb.data.remote.ApiService
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GenreRepository {
    override fun getGenreList() = Resource.fromSourceFlow {
        apiService.getGenreList()
    }
}