package com.example.swchallenge.data.remote

import com.example.swchallenge.data.remote.models.CatBreedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApiService {

    @GET(BREEDS_PATH)
    suspend fun fetchCatBreeds() : List<CatBreedDto>

    @GET(SEARCH_PATH)
    suspend fun fetchCatBreedsByName(@Query("name") name: String) : List<CatBreedDto>

    companion object {
        const val API_URL = "https://api.thecatapi.com/"
        const val BREEDS_PATH = "v1/breeds"
        const val SEARCH_PATH = "v1/breeds/search"

        const val IMAGE_PATH = "https://cdn2.thecatapi.com/images/"
        const val IMAGE_FORMAT = ".jpg"
    }
}