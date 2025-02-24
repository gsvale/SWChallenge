package com.example.swchallenge.domain.usecase

import com.example.swchallenge.core.Resource
import com.example.swchallenge.domain.CatsRepository
import com.example.swchallenge.domain.models.CatBreed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val catsRepository: CatsRepository) {

    fun getAllCats() : Flow<Resource<List<CatBreed>>> {
        return catsRepository.getAllCats()
    }

    fun getCatsByName(query: String) : Flow<Resource<List<CatBreed>>> {
        return catsRepository.getCatsByName(query)
    }

}