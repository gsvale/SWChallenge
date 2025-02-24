package com.example.swchallenge.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swchallenge.core.Resource
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.domain.usecase.GetCatsUseCase
import com.example.swchallenge.domain.usecase.UpdateFavouriteCatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _state = MutableStateFlow(CatsListUiState())
    val state: StateFlow<CatsListUiState> = _state.asStateFlow()

    init {
        loadCats()
    }

    private fun loadCats() {
        viewModelScope.launch {
            _state.update { it.copy(
                isSearching = false
            ) }
            getCatsUseCase.getAllCats().collect{ result ->
                if(!_state.value.isSearching){
                    when(result){
                        is Resource.Error -> {
                            _state.update { it.copy(
                                isLoading = false,
                                error = result.error,
                                catsList = result.data ?: emptyList()
                            ) }
                        }
                        is Resource.Loading -> {
                            _state.update { it.copy(
                                isLoading = true,
                                error = null,
                                catsList = result.data ?: emptyList()
                            ) }
                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = null,
                                    catsList = result.data ?: emptyList()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun searchCats(query: String){
        _searchQuery.value = query
        searchJob?.cancel()
        if(query.isNotEmpty()){
            searchJob = viewModelScope.launch {
                _state.update { it.copy(
                    isSearching = true
                ) }
                getCatsUseCase.getCatsByName(query).collect{ result ->
                    when(result){
                        is Resource.Error -> {
                            _state.update { it.copy(
                                isLoading = false,
                                error = result.error,
                                catsList = result.data ?: emptyList()
                            ) }
                        }
                        is Resource.Loading -> {
                            _state.update { it.copy(
                                isLoading = true,
                                error = null,
                                catsList = result.data ?: emptyList()
                            ) }
                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = null,
                                    catsList = result.data ?: emptyList()
                                )
                            }
                        }
                    }
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