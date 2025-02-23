package com.example.swchallenge.presentation.navigation

sealed class Screens(val route : String) {
    object CatList : Screens("cats_list_screen")
    object Favourites : Screens("favourites_screen")
    object Detail : Screens("cat_detail_screen")
}