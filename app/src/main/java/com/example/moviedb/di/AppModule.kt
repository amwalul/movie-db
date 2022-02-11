package com.example.moviedb.di

import com.example.moviedb.BuildConfig
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiService(): ApiService = ServiceFactory.makeService(BuildConfig.BASE_API_URL)
}