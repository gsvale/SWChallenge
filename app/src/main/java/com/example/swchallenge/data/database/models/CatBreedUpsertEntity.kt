package com.example.swchallenge.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatBreedUpsertEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    val name: String,
    val lifeSpan: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val imageId: String
)