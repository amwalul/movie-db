package com.example.moviedb.di

import com.example.moviedb.data.repository.genre.GenreRepository
import com.example.moviedb.data.repository.genre.GenreRepositoryImpl
import com.example.moviedb.data.repository.movie.MovieRepository
import com.example.moviedb.data.repository.movie.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideGenreRepository(genreRepositoryImpl: GenreRepositoryImpl): GenreRepository

    @Binds
    @Singleton
    fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}