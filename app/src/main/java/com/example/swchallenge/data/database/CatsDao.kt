package com.example.swchallenge.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.swchallenge.data.database.models.CatBreedEntity
import com.example.swchallenge.data.database.models.CatBreedUpsertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatsDao {

    @Upsert(entity = CatBreedEntity::class)
    suspend fun insertCatBreed(vararg catBreedUpsertEntity: CatBreedUpsertEntity)

    @Query("SELECT * FROM cats ORDER BY id DESC")
    fun getAllCatsBreeds() : Flow<List<CatBreedEntity>>

    @Query("SELECT * FROM cats WHERE name LIKE '%' || :query || '%' ORDER BY id DESC")
    fun getAllCatsByName(query: String) : Flow<List<CatBreedEntity>>

    @Update
    suspend fun updateCatBreed(catBreedEntity: CatBreedEntity)

    @Query("SELECT * FROM cats WHERE isFavourite = 1 ORDER BY id DESC")
    fun getFavouriteCatsBreeds() : Flow<List<CatBreedEntity>>
}