package com.example.moviedb.ui.screen.genre.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviedb.data.repository.genre.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreListViewModel @Inject constructor(
    genreRepository: GenreRepository
) : ViewModel() {

    val genreList = genreRepository.getGenreList().asLiveData()
}