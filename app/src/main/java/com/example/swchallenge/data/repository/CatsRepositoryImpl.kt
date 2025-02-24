package com.example.swchallenge.data.repository

import com.example.swchallenge.core.DataError
import com.example.swchallenge.core.Resource
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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val catsDao: CatsDao,
    private val catsApiService: CatsApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : CatsRepository {

    override fun getAllCats(): Flow<Resource<List<CatBreed>>> = flow {
        emit(Resource.Loading())

        val savedCatsBreeds = catsDao.getAllCatsBreeds().map { it.toCatBreeds() }.firstOrNull()
        emit(Resource.Loading(data = savedCatsBreeds))

        try {
            val fetchedCatBreeds = catsApiService.fetchCatBreeds()
            catsDao.insertCatBreed(*fetchedCatBreeds.toCatBreedUpsertEntities())
        } catch (e : HttpException){
            when (e.code()) {
                408 -> {
                    emit(
                        Resource.Error(
                            error = DataError.Network.REQUEST_TIMEOUT,
                            data = savedCatsBreeds
                        )
                    )
                }
                413 -> {
                    emit(
                        Resource.Error(
                            error = DataError.Network.PAYLOAD_TOO_LARGE,
                            data = savedCatsBreeds
                        )
                    )
                }
                else -> {
                    emit(
                        Resource.Error(
                            error = DataError.Network.UNKNOWN,
                            data = savedCatsBreeds
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    error = DataError.Network.NO_INTERNET
                )
            )
        }

        val newCatsBreeds = catsDao.getAllCatsBreeds().map { it.toCatBreeds() }
        newCatsBreeds.collect{
            emit(Resource.Success(data = it))
        }

    }.flowOn(ioDispatcher)

    override fun getCatsByName(query: String): Flow<Resource<List<CatBreed>>> = flow {

        val savedCatsBreeds = catsDao.getAllCatsByName(query).map { it.toCatBreeds() }.firstOrNull()

        try {
            val fetchedCatBreeds = catsApiService.fetchCatBreedsByName(query)
            catsDao.insertCatBreed(*fetchedCatBreeds.toCatBreedUpsertEntities())
        } catch (e : HttpException){
            when (e.code()) {
                408 -> {
                    emit(
                        Resource.Error(
                            error = DataError.Network.REQUEST_TIMEOUT,
                            data = savedCatsBreeds
                        )
                    )
                }
                413 -> {
                    emit(
                        Resource.Error(
                            error = DataError.Network.PAYLOAD_TOO_LARGE,
                            data = savedCatsBreeds
                        )
                    )
                }
                else -> {
                    emit(
                        Resource.Error(
                            error = DataError.Network.UNKNOWN,
                            data = savedCatsBreeds
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    error = DataError.Network.NO_INTERNET
                )
            )
        }

        val newCatsBreeds = catsDao.getAllCatsByName(query).map { it.toCatBreeds() }
        newCatsBreeds.collect {
            emit(Resource.Success(data = it))
        }

    }.flowOn(ioDispatcher)

    override fun getFavouriteCats(): Flow<List<CatBreed>> = flow {
        val savedCatBreeds = catsDao.getFavouriteCatsBreeds()
        savedCatBreeds.collect {
            emit(it.toCatBreeds())
        }
    }.distinctUntilChanged().flowOn(ioDispatcher)

    override suspend fun updateFavourite(catBreed: CatBreed) {
        val catBreedEntity = catBreed.toCatBreedEntity()
        catsDao.updateCatBreed(catBreedEntity)
    }

    override fun getCatById(id: String): Flow<CatBreed> = flow {
        val catBreedEntity = catsDao.getCatBreedById(id)
        catBreedEntity.collect {
            emit(it.toCatBreed())
        }
    }.distinctUntilChanged().flowOn(ioDispatcher)
}