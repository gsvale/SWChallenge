package com.example.swchallenge.presentation.list

import com.example.swchallenge.core.DataError
import com.example.swchallenge.domain.models.CatBreed

data class CatsListUiState (
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val error : DataError? = null,
    val catsList: List<CatBreed> = emptyList()
)