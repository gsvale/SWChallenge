package com.example.swchallenge.domain

import com.example.swchallenge.core.Resource
import com.example.swchallenge.domain.models.CatBreed
import kotlinx.coroutines.flow.Flow

interface CatsRepository {
    fun getAllCats(): Flow<Resource<List<CatBreed>>>
    fun getCatsByName(query: String): Flow<Resource<List<CatBreed>>>
    fun getFavouriteCats(): Flow<List<CatBreed>>
    suspend fun updateFavourite(catBreed: CatBreed)
    fun getCatById(id: String): Flow<CatBreed>
}