package com.example.swchallenge.di

import com.example.swchallenge.data.database.CatsDao
import com.example.swchallenge.data.remote.CatsApiService
import com.example.swchallenge.data.repository.CatsRepositoryImpl
import com.example.swchallenge.domain.CatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideCatsRepository(
        dao: CatsDao,
        catsApiService: CatsApiService,
        @IODispatcher ioDispatcher: CoroutineDispatcher) : CatsRepository {
        return CatsRepositoryImpl(dao, catsApiService, ioDispatcher)
    }

}