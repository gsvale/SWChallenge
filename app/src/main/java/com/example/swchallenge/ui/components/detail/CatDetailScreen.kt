package com.example.swchallenge.ui.components.detail


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.swchallenge.R
import com.example.swchallenge.data.remote.CatsApiService
import com.example.swchallenge.presentation.detail.CatDetailViewModel

@Composable
fun CatDetailScreen(
    id: String,
    viewModel: CatDetailViewModel
) {

    LaunchedEffect(id) {
        viewModel.loadCatBreed(id)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value

    ConstraintLayout(Modifier.fillMaxSize()) {

        val (loadingRef,
            titleRef,
            favouriteRef,
            imageRef,
            originLabelRef,
            originRef,
            temperamentLabelRef,
            temperamentRef,
            descriptionLabelRef,
            descriptionRef) = createRefs()

        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .constrainAs(loadingRef) {
                            centerHorizontallyTo(parent)
                            centerVerticallyTo(parent)
                        }
                        .size(100.dp),
                    color = Color.Green
                )
            }

            else -> {
                state.catBreed?.let { catBreed ->

                    val topTitleGuideline = createGuidelineFromTop(0.05f)
                    val startTitleGuideline = createGuidelineFromStart(0.1f)
                    val endFavouriteGuideline = createGuidelineFromEnd(0.05f)

                    Text(
                        catBreed.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        modifier = Modifier.constrainAs(titleRef) {
                            top.linkTo(topTitleGuideline)
                            start.linkTo(startTitleGuideline)
                        }
                    )

                    IconButton(
                        modifier = Modifier.constrainAs(favouriteRef) {
                            top.linkTo(topTitleGuideline)
                            end.linkTo(endFavouriteGuideline)
                        },
                        onClick = { viewModel.updateFavourite(catBreed) }
                    ) {
                        if (catBreed.isFavourite) {
                            Icon(
                                painter = painterResource(R.drawable.start_filled),
                                null,
                                modifier = Modifier.size(200.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.star_outline),
                                null,
                                modifier = Modifier.size(200.dp)
                            )
                        }
                    }

                    AsyncImage(
                        model = CatsApiService.IMAGE_PATH + catBreed.imageId + CatsApiService.IMAGE_FORMAT,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        error = painterResource(R.drawable.ic_launcher_background),
                        modifier = Modifier
                            .size(250.dp)
                            .constrainAs(imageRef) {
                                top.linkTo(titleRef.bottom, margin = 25.dp)
                                centerHorizontallyTo(parent)
                            }
                    )

                    Text(
                        stringResource(R.string.origin_label),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.constrainAs(originLabelRef) {
                            top.linkTo(imageRef.bottom, margin = 25.dp)
                            centerHorizontallyTo(parent)
                        }
                    )

                    Text(
                        catBreed.origin,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        modifier = Modifier.constrainAs(originRef) {
                            top.linkTo(originLabelRef.bottom, margin = 5.dp)
                            centerHorizontallyTo(parent)
                        },
                        textAlign = TextAlign.Center
                    )

                    Text(
                        stringResource(R.string.temperament_label),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.constrainAs(temperamentLabelRef) {
                            top.linkTo(originRef.bottom, margin = 10.dp)
                            centerHorizontallyTo(parent)
                        }
                    )

                    Text(
                        catBreed.temperament,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        modifier = Modifier.constrainAs(temperamentRef) {
                            top.linkTo(temperamentLabelRef.bottom, margin = 5.dp)
                            centerHorizontallyTo(parent)
                        },
                        textAlign = TextAlign.Center
                    )

                    Text(
                        stringResource(R.string.description_label),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.constrainAs(descriptionLabelRef) {
                            top.linkTo(temperamentRef.bottom, margin = 10.dp)
                            centerHorizontallyTo(parent)
                        }
                    )

                    Text(
                        catBreed.description,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        modifier = Modifier.constrainAs(descriptionRef) {
                            top.linkTo(descriptionLabelRef.bottom, margin = 5.dp)
                            centerHorizontallyTo(parent)
                        },
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}