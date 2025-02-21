package com.example.swchallenge.data.remote

import javax.inject.Inject

class CatsApiServiceImpl @Inject constructor(private val catsApiService: CatsApiService) {

    suspend fun fetchCatBreeds() = catsApiService.fetchCatBreeds()

    suspend fun fetchBreedsByName(name: String) = catsApiService.fetchBreedsByName(name)
}