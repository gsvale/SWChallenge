package com.example.swchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.swchallenge.presentation.detail.CatDetailViewModel
import com.example.swchallenge.presentation.favourites.FavouritesViewModel
import com.example.swchallenge.presentation.list.CatsListViewModel
import com.example.swchallenge.ui.components.navigation.MainNavigation
import com.example.swchallenge.ui.theme.SWChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val catsListViewModel: CatsListViewModel by viewModels()
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private val catDetailViewModel: CatDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SWChallengeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainNavigation(
                        catsListViewModel,
                        favouritesViewModel,
                        catDetailViewModel
                    )
                }
            }
        }
    }
}