package com.example.swchallenge.ui.components.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swchallenge.R
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.presentation.favourites.FavouritesViewModel
import com.example.swchallenge.ui.components.item.CatItem

@Composable
fun FavouritesScreen(viewModel: FavouritesViewModel, onItemClick : (CatBreed) -> Unit) {

    val favouritesList = viewModel.favouritesList.collectAsStateWithLifecycle().value

    ConstraintLayout(Modifier.fillMaxSize()) {

        val (titleRef, averageLifeSpanRef, gridRef) = createRefs()

        val topTitleGuideline = createGuidelineFromTop(0.05f)
        val startTitleGuideline = createGuidelineFromStart(0.1f)

        val startAverageLifeSpanGuideline = createGuidelineFromStart(0.05f)
        val endAverageLifeSpanGuideline = createGuidelineFromEnd(0.05f)

        Text(
            stringResource(R.string.favourites_label),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier =  Modifier.constrainAs(titleRef) {
                top.linkTo(topTitleGuideline)
                start.linkTo(startTitleGuideline)
            }
        )

        Text(
            stringResource(R.string.average_life_span_label, viewModel.averageLifeSpan.value),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(averageLifeSpanRef) {
                top.linkTo(titleRef.bottom, margin = 25.dp)
                start.linkTo(startAverageLifeSpanGuideline)
                end.linkTo(endAverageLifeSpanGuideline)
                width = Dimension.fillToConstraints
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier.constrainAs(gridRef){
                top.linkTo(averageLifeSpanRef.bottom, margin = 25.dp)
                start.linkTo(startAverageLifeSpanGuideline)
                end.linkTo(endAverageLifeSpanGuideline)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            items(favouritesList.size) { item ->
                CatItem(
                    favouritesList[item],
                    onClickFavourite = { viewModel.updateFavourite(it) },
                    onClickItem = { onItemClick(it)}
                )
            }
        }
    }
}