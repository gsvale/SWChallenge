package com.example.swchallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.swchallenge.data.database.models.CatBreedEntity

@Database(entities = [CatBreedEntity::class], version = 1, exportSchema = false)
abstract class CatsRoomDatabase : RoomDatabase() {
    abstract fun catsDao(): CatsDao

    companion object {
        const val DATABASE_NAME = "cats_database"
    }
}