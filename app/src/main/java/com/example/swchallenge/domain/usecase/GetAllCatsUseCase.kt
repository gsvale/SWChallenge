package com.example.swchallenge.domain.usecase

import com.example.swchallenge.domain.CatsRepository
import javax.inject.Inject

class GetAllCatsUseCase @Inject constructor(private val catsRepository: CatsRepository) {

}