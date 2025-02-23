package com.example.swchallenge.domain

import com.example.swchallenge.domain.models.CatBreed
import kotlinx.coroutines.flow.Flow

interface CatsRepository {
    fun getAllCats(): Flow<List<CatBreed>>
    fun getCatsByName(query: String): Flow<List<CatBreed>>
    fun getFavouriteCats(): Flow<List<CatBreed>>
    suspend fun updateFavourite(catBreed: CatBreed)
}