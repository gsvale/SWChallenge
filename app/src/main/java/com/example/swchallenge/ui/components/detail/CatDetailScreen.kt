package com.example.swchallenge.ui.components.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.presentation.detail.CatDetailViewModel

@Composable
fun CatDetailScreen(
    catBreed: CatBreed,
    viewModel: CatDetailViewModel
) {

    Text(text = catBreed.name)
}