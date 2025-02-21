package com.example.swchallenge.di

import com.example.swchallenge.data.remote.CatsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKotlinSerialization() : Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    fun provideCatsApiService(converterFactory : Converter.Factory): CatsApiService {
        return Retrofit.Builder()
            .baseUrl(CatsApiService.API_URL)
            .addConverterFactory(converterFactory)
            .build()
            .create(CatsApiService::class.java)
    }
}