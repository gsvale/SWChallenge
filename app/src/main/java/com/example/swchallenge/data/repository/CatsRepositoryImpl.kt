package com.example.swchallenge.data.repository

import com.example.swchallenge.data.database.CatsDao
import com.example.swchallenge.data.remote.CatsApiService
import com.example.swchallenge.di.IODispatcher
import com.example.swchallenge.domain.CatsRepository
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.domain.toCatBreed
import com.example.swchallenge.domain.toCatBreedEntity
import com.example.swchallenge.domain.toCatBreedUpsertEntities
import com.example.swchallenge.domain.toCatBreeds
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val catsDao: CatsDao,
    private val catsApiService: CatsApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : CatsRepository {

    override fun getAllCats(): Flow<List<CatBreed>> = flow {
        val fetchedCatBreeds = catsApiService.fetchCatBreeds()
        catsDao.insertCatBreed(*fetchedCatBreeds.toCatBreedUpsertEntities())
        val savedCatBreeds = catsDao.getAllCatsBreeds()
        savedCatBreeds.collect{
            emit(it.toCatBreeds())
        }
    }.distinctUntilChanged().flowOn(ioDispatcher)

    override fun getCatsByName(query: String): Flow<List<CatBreed>> = flow {
        val fetchedCatBreeds = catsApiService.fetchCatBreedsByName(query)
        catsDao.insertCatBreed(*fetchedCatBreeds.toCatBreedUpsertEntities())
        val savedCatBreeds = catsDao.getAllCatsByName(query)
        savedCatBreeds.collect{
            emit(it.toCatBreeds())
        }
    }.distinctUntilChanged().flowOn(ioDispatcher)

    override fun getFavouriteCats(): Flow<List<CatBreed>> = flow {
        val savedCatBreeds = catsDao.getFavouriteCatsBreeds()
        savedCatBreeds.collect{
            emit(it.toCatBreeds())
        }
    }.distinctUntilChanged().flowOn(ioDispatcher)

    override suspend fun updateFavourite(catBreed: CatBreed) {
        val catBreedEntity = catBreed.toCatBreedEntity()
        catsDao.updateCatBreed(catBreedEntity)
    }

    override fun getCatById(id: String): Flow<CatBreed> = flow {
        val catBreedEntity = catsDao.getCatBreedById(id)
        catBreedEntity.collect{
            emit(it.toCatBreed())
        }
    }.distinctUntilChanged().flowOn(ioDispatcher)
}