package com.example.swchallenge.presentation.detail

import app.cash.turbine.test
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.domain.usecase.UpdateFavouriteCatUseCase
import com.example.swchallenge.utils.CoroutineTestRule
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CatDetailViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var updateFavouriteCatUseCase: UpdateFavouriteCatUseCase

    private lateinit var underTest: CatDetailViewModel

    @Before
    fun setUp() {
        updateFavouriteCatUseCase = mockk(relaxUnitFun = true)
        underTest = CatDetailViewModel(updateFavouriteCatUseCase)
    }

    @Test
    fun `GIVEN cat breed id WHEN loadCatBreed is called THEN collect respective cat breed item`() = runTest {

        // GIVEN
        val id = "id"
        val catBreed = CatBreed(
            id,
            "name",
            "lifeSpan",
            "origin",
            "temperament",
            "description",
            "imageId",
            true)
        every { updateFavouriteCatUseCase.getCatById(id) } returns flowOf(catBreed)

        // WHEN
        underTest.loadCatBreed(id)

        // THEN
        underTest.state.test {
            assertEquals(CatDetailUiState(isLoading = false, catBreed = null), awaitItem())
            assertEquals(CatDetailUiState(isLoading = true, catBreed = null), awaitItem())
            assertEquals(CatDetailUiState(isLoading = false, catBreed = catBreed), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        runCurrent()
        verify { updateFavouriteCatUseCase.getCatById(id) }
    }

    @Test
    fun `GIVEN cat breed WHEN updateFavourite is called THEN update current cat breed correctly`() = runTest {

        // GIVEN
        val catBreed = CatBreed(
            "id",
            "name",
            "lifeSpan",
            "origin",
            "temperament",
            "description",
            "imageId",
            true)

        // WHEN
        underTest.updateFavourite(catBreed)

        // THEN
        runCurrent()
        coVerify { updateFavouriteCatUseCase.updateFavourite(catBreed) }
        underTest.state.test {
            assertEquals(CatDetailUiState(isLoading = false, catBreed = null), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}