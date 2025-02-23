package com.example.swchallenge.domain.usecase

import com.example.swchallenge.domain.CatsRepository
import com.example.swchallenge.domain.models.CatBreed
import javax.inject.Inject

class UpdateFavouriteCatUseCase @Inject constructor(private val catsRepository: CatsRepository) {

    suspend fun updateFavourite(catBreed: CatBreed) {
        catsRepository.updateFavourite(catBreed)
    }
}