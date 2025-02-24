package com.example.swchallenge.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.domain.usecase.UpdateFavouriteCatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val updateFavouriteCatUseCase : UpdateFavouriteCatUseCase) : ViewModel() {

    private val _state = MutableStateFlow(CatDetailUiState())
    val state: StateFlow<CatDetailUiState> = _state.asStateFlow()

    fun loadCatBreed(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, catBreed = null)
            }
            updateFavouriteCatUseCase.getCatById(id).collect{ item ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        catBreed = item
                    )
                }
            }
        }
    }

    fun updateFavourite(catBreed: CatBreed){
        viewModelScope.launch {
            updateFavouriteCatUseCase.updateFavourite(catBreed)
        }
    }
}