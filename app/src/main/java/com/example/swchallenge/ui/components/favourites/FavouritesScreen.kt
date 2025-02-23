package com.example.swchallenge.ui.components.favourites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.swchallenge.R

@Composable
fun FavouritesScreen(navController: NavController) {
    ConstraintLayout(Modifier.fillMaxSize()) {

        val (titleRef) = createRefs()

        val topTitleGuideline = createGuidelineFromTop(0.05f)
        val startTitleGuideline = createGuidelineFromStart(0.1f)

        Text(
            stringResource(R.string.favourites_label),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier =  Modifier.constrainAs(titleRef) {
                top.linkTo(topTitleGuideline)
                start.linkTo(startTitleGuideline)
            }
        )
    }
}