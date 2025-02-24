package com.example.swchallenge.presentation.list

import com.example.swchallenge.domain.models.CatBreed

data class CatsListUiState (
    val isLoading: Boolean = false,
    val catsList: List<CatBreed> = emptyList()
)