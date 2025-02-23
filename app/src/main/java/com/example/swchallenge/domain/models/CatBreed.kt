package com.example.swchallenge.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatBreed(
    val id: String,
    val name: String,
    val lifeSpan: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val imageId: String,
    val isFavourite: Boolean
) : Parcelable
