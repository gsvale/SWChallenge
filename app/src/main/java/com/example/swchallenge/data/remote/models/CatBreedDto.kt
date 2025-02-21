package com.example.swchallenge.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CatBreedDto(
    @SerialName("name") val name: String,
    @SerialName("life_span") val lifeSpan: String,
    @SerialName("origin") val origin: String,
    @SerialName("temperament") val temperament: String,
    @SerialName("description") val description: String,
    @SerialName("reference_image_id") val imageId: String? = ""
)
