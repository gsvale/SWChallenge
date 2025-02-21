package com.example.swchallenge.data.remote

import com.example.swchallenge.core.Constants.BREEDS_PATH
import com.example.swchallenge.core.Constants.SEARCH_PATH
import com.example.swchallenge.data.remote.models.CatBreedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApiService {

    @GET(BREEDS_PATH)
    suspend fun fetchCatBreeds() : List<CatBreedDto>

    @GET(SEARCH_PATH)
    suspend fun fetchBreedsByName(@Query("name") name: String) : List<CatBreedDto>
}