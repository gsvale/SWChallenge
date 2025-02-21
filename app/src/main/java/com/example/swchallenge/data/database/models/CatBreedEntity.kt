package com.example.swchallenge.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class CatBreedEntity(
    @PrimaryKey val id: String,
    val name: String,
    val lifeSpan: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val imageId: String,
    val isFavourite: Boolean
)
