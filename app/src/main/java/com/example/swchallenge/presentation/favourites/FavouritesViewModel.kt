package com.example.swchallenge.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.domain.usecase.GetFavouriteCatsUseCase
import com.example.swchallenge.domain.usecase.UpdateFavouriteCatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val useCase: GetFavouriteCatsUseCase,
    private val updateFavouriteCatUseCase : UpdateFavouriteCatUseCase
) : ViewModel() {

    private val _favouritesList : MutableStateFlow<List<CatBreed>> = MutableStateFlow(emptyList())
    val favouritesList: StateFlow<List<CatBreed>> = _favouritesList.asStateFlow()

    init {
        loadFavourites()
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            useCase.getFavouriteCats().collect{
                _favouritesList.value = it
            }
        }
    }

    fun updateFavourite(catBreed: CatBreed){
        viewModelScope.launch {
            updateFavouriteCatUseCase.updateFavourite(catBreed)
        }
    }
}