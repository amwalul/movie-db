package com.example.moviedb.ui.screen.movie.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.moviedb.data.Resource
import com.example.moviedb.data.remote.model.movie.detail.MovieResponse
import com.example.moviedb.data.remote.model.movie.review.MovieReviewListData
import com.example.moviedb.data.remote.model.movie.video.MovieVideoListResponse
import com.example.moviedb.data.repository.movie.MovieRepository
import com.example.moviedb.extension.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movie = MediatorLiveData<Resource<MovieResponse>>()
    val movie get() = _movie.toLiveData()

    private val _movieVideoList = MediatorLiveData<Resource<MovieVideoListResponse>>()
    val movieVideoList get() = _movieVideoList.toLiveData()

    private val _movieReviewList = MediatorLiveData<PagingData<MovieReviewListData>>()
    val movieReviewList get() = _movieReviewList

    fun getMovie(id: Int) {
        _movie.addSource(movieRepository.getMovie(id).asLiveData()) {
            _movie.value = it
        }
    }

    fun getMovieVideoList(id: Int) {
        _movieVideoList.addSource(movieRepository.getMovieVideoList(id).asLiveData()) {
            _movieVideoList.value = it
        }
    }

    fun getMovieReviewList(id: Int) {
        _movieReviewList.addSource(
            movieRepository.getMovieReviewList(id, viewModelScope).asLiveData()
        ) {
            _movieReviewList.value = it
        }
    }
}