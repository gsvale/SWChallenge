package com.example.swchallenge.presentation.favourites

import com.example.swchallenge.domain.models.CatBreed

data class FavouritesUiState (
    val isLoading: Boolean = false,
    val catsList: List<CatBreed> = emptyList()
)