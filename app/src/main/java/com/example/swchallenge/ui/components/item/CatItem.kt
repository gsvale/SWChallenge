package com.example.swchallenge.ui.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.example.swchallenge.R
import com.example.swchallenge.data.remote.CatsApiService
import com.example.swchallenge.domain.models.CatBreed

@Composable
fun CatItem(
    catBreed: CatBreed,
    onClickFavourite: (CatBreed) -> Unit,
    onClickItem: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem(catBreed.id) }
    ) {

        val (imageRef, nameRef, favouriteRef) = createRefs()

        AsyncImage(
            model = CatsApiService.IMAGE_PATH + catBreed.imageId + CatsApiService.IMAGE_FORMAT,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            error = painterResource(R.drawable.ic_launcher_background),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                }
        )

        IconButton(
            modifier = Modifier.constrainAs(favouriteRef) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.end)
                bottom.linkTo(parent.baseline)
            },
            onClick = { onClickFavourite(catBreed) }
        ) {
            if (catBreed.isFavourite) {
                Icon(
                    painter = painterResource(R.drawable.start_filled),
                    null,
                    modifier = Modifier.size(40.dp)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.star_outline),
                    null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }


        Text(
            catBreed.name,
            modifier = Modifier
                .constrainAs(nameRef) {
                    top.linkTo(imageRef.bottom, margin = 5.dp)
                }
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}