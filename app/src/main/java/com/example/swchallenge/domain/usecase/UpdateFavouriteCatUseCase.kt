package com.example.swchallenge.domain.usecase

import com.example.swchallenge.domain.CatsRepository
import com.example.swchallenge.domain.models.CatBreed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFavouriteCatUseCase @Inject constructor(private val catsRepository: CatsRepository) {

    suspend fun updateFavourite(catBreed: CatBreed) {
        catsRepository.updateFavourite(catBreed)
    }

    fun getCatById(id: String) : Flow<CatBreed>{
        return catsRepository.getCatById(id)
    }
}