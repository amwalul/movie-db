package com.example.moviedb.ui.screen.movie.list

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.moviedb.data.remote.model.movie.list.MovieListData
import com.example.moviedb.data.repository.movie.MovieRepository
import com.example.moviedb.extension.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieList = MediatorLiveData<PagingData<MovieListData>>()
    val movieList get() = _movieList.toLiveData()

    fun getMovieList(genreId: Int) {
        _movieList.addSource(
            movieRepository.getMovieListByGenre(genreId, viewModelScope).asLiveData()
        ) {
            _movieList.value = it
        }
    }
}