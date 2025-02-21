package com.example.swchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.swchallenge.data.database.CatsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCatsDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            CatsRoomDatabase::class.java,
            CatsRoomDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigrationFrom().build()

    @Provides
    @Singleton
    fun provideCatsDao(database: CatsRoomDatabase) = database.catsDao()

}