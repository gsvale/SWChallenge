package com.example.swchallenge.domain.usecase

import com.example.swchallenge.domain.CatsRepository
import javax.inject.Inject

class GetFavouriteCatsUseCase @Inject constructor(private val catsRepository: CatsRepository)