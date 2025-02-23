package com.example.swchallenge.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.domain.usecase.GetCatsUseCase
import com.example.swchallenge.domain.usecase.UpdateFavouriteCatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatsListViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val updateFavouriteCatUseCase : UpdateFavouriteCatUseCase
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private var searchJob: Job? = null

    private val _catsList : MutableStateFlow<List<CatBreed>> = MutableStateFlow(emptyList())
    val catsList: StateFlow<List<CatBreed>> = _catsList.asStateFlow()

    init {
        loadCats()
    }

    private fun loadCats() {
        viewModelScope.launch {
            getCatsUseCase.getAllCats().collect{
                _catsList.value = it
            }
        }
    }

    fun searchCats(query: String){
        _searchQuery.value = query
        searchJob?.cancel()
        if(query.isNotEmpty()){
            searchJob = viewModelScope.launch {
                getCatsUseCase.getCatsByName(query).collect{
                    _catsList.value = it
                }
            }
        } else{
            loadCats()
        }
    }

    fun updateFavourite(catBreed: CatBreed){
        viewModelScope.launch {
            updateFavouriteCatUseCase.updateFavourite(catBreed)
        }
    }
}