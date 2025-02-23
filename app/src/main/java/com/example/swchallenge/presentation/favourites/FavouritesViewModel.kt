package com.example.swchallenge.presentation.favourites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _averageLifeSpan = mutableStateOf("")
    val averageLifeSpan: State<String> = _averageLifeSpan

    init {
        loadFavourites()
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            useCase.getFavouriteCats().collect{
                _favouritesList.value = it
                updateAverageLifeSpan()
            }
        }
    }

    private fun updateAverageLifeSpan(){
        val favouritesList = _favouritesList.value
        if(favouritesList.isNotEmpty()){
            var averageLifeSpanValue = 0
            for(item in favouritesList){
                var itemLifeSpan = item.lifeSpan
                itemLifeSpan = itemLifeSpan.replace(" ", "")
                val itemLifeSpanValues = itemLifeSpan.split("-")
                averageLifeSpanValue += itemLifeSpanValues[1].toInt()
            }
            _averageLifeSpan.value = (averageLifeSpanValue / favouritesList.size).toString()
        } else{
            _averageLifeSpan.value = ""
        }
    }

    fun updateFavourite(catBreed: CatBreed){
        viewModelScope.launch {
            updateFavouriteCatUseCase.updateFavourite(catBreed)
        }
    }
}