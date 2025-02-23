package com.example.swchallenge.domain

import com.example.swchallenge.data.database.models.CatBreedEntity
import com.example.swchallenge.data.database.models.CatBreedUpsertEntity
import com.example.swchallenge.data.remote.models.CatBreedDto
import com.example.swchallenge.domain.models.CatBreed

fun List<CatBreedDto>.toCatBreedUpsertEntities(): Array<CatBreedUpsertEntity> {
    return map {
        CatBreedUpsertEntity(
            id = it.id,
            name = it.name,
            lifeSpan = it.lifeSpan,
            origin = it.origin,
            temperament = it.temperament,
            description = it.description,
            imageId = it.imageId ?: ""
        )
    }.toTypedArray()
}

fun List<CatBreedEntity>.toCatBreeds(): List<CatBreed> {
    return map {
        CatBreed(
            id = it.id,
            name = it.name,
            lifeSpan = it.lifeSpan,
            origin = it.origin,
            temperament = it.temperament,
            description = it.description,
            imageId = it.imageId,
            isFavourite = it.isFavourite
        )
    }
}

fun CatBreed.toCatBreedEntity() : CatBreedEntity {
    return CatBreedEntity(
        id = this.id,
        name = this.name,
        lifeSpan = this.lifeSpan,
        origin = this.origin,
        temperament = this.temperament,
        description = this.description,
        imageId = this.imageId,
        isFavourite = !this.isFavourite
    )
}