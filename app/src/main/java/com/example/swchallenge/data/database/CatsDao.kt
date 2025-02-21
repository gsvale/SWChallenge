package com.example.swchallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.swchallenge.data.database.models.CatBreedEntity

@Dao
interface CatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCatBreed(catBreedEntity: CatBreedEntity)

    @Query("SELECT * FROM cats ORDER BY id DESC")
    fun getAllCatsBreeds() : List<CatBreedEntity>

    @Query("SELECT * FROM cats WHERE isFavourite = 1 ORDER BY id DESC")
    fun getFavouriteCatsBreeds() : List<CatBreedEntity>

    @Update
    fun updateFavouriteCatBreed(catBreedEntity: CatBreedEntity)
}