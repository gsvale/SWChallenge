package com.example.swchallenge.data.repository

import com.example.swchallenge.data.database.CatsDao
import com.example.swchallenge.data.remote.CatsApiService
import com.example.swchallenge.domain.CatsRepository
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val catsDao: CatsDao,
    private val catsApiService: CatsApiService
) : CatsRepository {

}