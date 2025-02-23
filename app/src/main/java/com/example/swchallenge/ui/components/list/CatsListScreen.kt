package com.example.swchallenge.ui.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.swchallenge.presentation.list.CatsListViewModel
import com.example.swchallenge.ui.components.item.CatItem


@Composable
fun CatsListScreen(viewModel : CatsListViewModel, onItemClick : (CatBreed) -> Unit){

   val catsList = viewModel.catsList.collectAsStateWithLifecycle().value

   ConstraintLayout(Modifier.fillMaxSize()) {

      val (titleRef, searchRef, gridRef) = createRefs()

      val topTitleGuideline = createGuidelineFromTop(0.05f)
      val startTitleGuideline = createGuidelineFromStart(0.1f)

      val startSearchGuideline = createGuidelineFromStart(0.05f)
      val endSearchGuideline = createGuidelineFromEnd(0.05f)

      Text(
         stringResource(R.string.cats_app_label),
         fontWeight = FontWeight.Bold,
         fontSize = 30.sp,
         modifier = Modifier.constrainAs(titleRef) {
            top.linkTo(topTitleGuideline)
            start.linkTo(startTitleGuideline)
         }
      )

      TextField(
         value = viewModel.searchQuery.value,
         onValueChange = viewModel::searchCats,
         modifier = Modifier.constrainAs(searchRef){
            top.linkTo(titleRef.bottom, margin = 25.dp)
            start.linkTo(startSearchGuideline)
            end.linkTo(endSearchGuideline)
            width = Dimension.fillToConstraints
         },
         placeholder = {
            Text(text = stringResource(R.string.search_label))
         }
      )


      LazyVerticalGrid(
         columns = GridCells.Fixed(3),
         horizontalArrangement = Arrangement.spacedBy(25.dp),
         verticalArrangement = Arrangement.spacedBy(25.dp),
         modifier = Modifier.constrainAs(gridRef){
            top.linkTo(searchRef.bottom, margin = 25.dp)
            start.linkTo(startSearchGuideline)
            end.linkTo(endSearchGuideline)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
         }
      ) {
         items(catsList.size) { item ->
            CatItem(
               catsList[item],
               onClickFavourite = { viewModel.updateFavourite(it) },
               onClickItem = { onItemClick(it)}
            )
         }
      }
   }
}