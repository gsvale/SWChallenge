package com.example.swchallenge.domain.usecase

import com.example.swchallenge.domain.CatsRepository
import com.example.swchallenge.domain.models.CatBreed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteCatsUseCase @Inject constructor(private val catsRepository: CatsRepository) {

    fun getFavouriteCats() : Flow<List<CatBreed>> {
        return catsRepository.getFavouriteCats()
    }
}