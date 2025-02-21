package com.example.swchallenge.di

import com.example.swchallenge.data.database.CatsDao
import com.example.swchallenge.data.remote.CatsApiService
import com.example.swchallenge.data.repository.CatsRepositoryImpl
import com.example.swchallenge.domain.CatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCatsRepository(dao: CatsDao, catsApiService: CatsApiService) : CatsRepository {
        return CatsRepositoryImpl(dao, catsApiService)
    }

}