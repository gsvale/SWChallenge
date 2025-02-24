package com.example.swchallenge.presentation.detail

import com.example.swchallenge.domain.models.CatBreed

data class CatDetailUiState (
    val isLoading: Boolean = false,
    val catBreed: CatBreed? = null
)