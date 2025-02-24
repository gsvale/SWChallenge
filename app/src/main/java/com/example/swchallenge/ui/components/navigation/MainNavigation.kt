package com.example.swchallenge.ui.components.navigation

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.swchallenge.R
import com.example.swchallenge.presentation.detail.CatDetailViewModel
import com.example.swchallenge.presentation.favourites.FavouritesViewModel
import com.example.swchallenge.presentation.list.CatsListViewModel
import com.example.swchallenge.presentation.navigation.Screens
import com.example.swchallenge.ui.components.detail.CatDetailScreen
import com.example.swchallenge.ui.components.favourites.FavouritesScreen
import com.example.swchallenge.ui.components.list.CatsListScreen


@Composable
fun MainNavigation(
    catsListViewModel : CatsListViewModel,
    favouritesViewModel: FavouritesViewModel,
    catDetailViewModel: CatDetailViewModel
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val previousBackStackEntry = navController.previousBackStackEntry
    val currentDestination = currentBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if(currentDestination?.route != Screens.Detail.route){
                NavigationBar(modifier = Modifier.height(IntrinsicSize.Min)) {
                    NavigationBarItem(
                        selected = currentDestination?.route == Screens.CatList.route,
                        label = { Text(stringResource(R.string.cats_list_label), fontSize = 15.sp, fontWeight = FontWeight.Bold) },
                        icon = {},
                        onClick = {
                            navController.navigate(Screens.CatList.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .padding(top = 20.dp, bottom = 20.dp)
                    )
                    NavigationBarItem(
                        selected = currentDestination?.route == Screens.Favourites.route,
                        label = { Text(stringResource(R.string.favourites_label), fontSize = 15.sp, fontWeight = FontWeight.Bold) },
                        icon = {},
                        onClick = {
                            navController.navigate(Screens.Favourites.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screens.CatList.route,
            modifier = Modifier.padding(paddingValues)) {

            composable(Screens.CatList.route) {
                CatsListScreen(
                    catsListViewModel
                ) {
                    currentBackStackEntry?.savedStateHandle?.set("id", it)
                    navController.navigate(Screens.Detail.route)
                }
            }

            composable(Screens.Favourites.route) {
                FavouritesScreen(
                    favouritesViewModel
                ) {
                    currentBackStackEntry?.savedStateHandle?.set("id", it)
                    navController.navigate(Screens.Detail.route)
                }
            }

            composable(Screens.Detail.route) {
                val catBreedId = previousBackStackEntry?.savedStateHandle?.get<String>("id")
                catBreedId?.let {
                    CatDetailScreen(
                        it,
                        catDetailViewModel
                    )
                }
            }
        }
    }
}