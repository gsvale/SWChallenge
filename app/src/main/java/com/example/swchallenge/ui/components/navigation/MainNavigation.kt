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
import com.example.swchallenge.presentation.list.CatsListViewModel
import com.example.swchallenge.presentation.navigation.Screens
import com.example.swchallenge.ui.components.favourites.FavouritesScreen
import com.example.swchallenge.ui.components.list.CatsListScreen


@Composable
fun MainNavigation(
    catsListViewModel : CatsListViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
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
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screens.CatList.route,
            modifier = Modifier.padding(paddingValues)) {

            composable(Screens.CatList.route) {
                CatsListScreen(
                    navController,
                    catsListViewModel
                )
            }

            composable(Screens.Favourites.route) {
                FavouritesScreen(
                    navController
                )
            }
        }
    }
}