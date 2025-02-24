package com.example.swchallenge.presentation.favourites

import app.cash.turbine.test
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.domain.usecase.GetFavouriteCatsUseCase
import com.example.swchallenge.domain.usecase.UpdateFavouriteCatUseCase
import com.example.swchallenge.utils.CoroutineTestRule
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
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
class FavouritesViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var getFavouriteCatsUseCase: GetFavouriteCatsUseCase
    private lateinit var updateFavouriteCatUseCase: UpdateFavouriteCatUseCase

    private val catBreed = CatBreed(
        "id",
        "name",
        "14 - 15",
        "origin",
        "temperament",
        "description",
        "imageId",
        false)

    private lateinit var underTest: FavouritesViewModel

    @Before
    fun setUp() {
        getFavouriteCatsUseCase = mockk(relaxUnitFun = true)
        updateFavouriteCatUseCase = mockk(relaxUnitFun = true)
    }

    @Test
    fun `GIVEN favourite list of cats in database THEN load them when view model is initialized`() = runTest {

        // GIVEN
        underTest = FavouritesViewModel(getFavouriteCatsUseCase, updateFavouriteCatUseCase)
        every { getFavouriteCatsUseCase.getFavouriteCats() } returns flowOf(listOf(catBreed))

        // THEN
        runCurrent()
        underTest.state.test {
            assertEquals(FavouritesUiState(isLoading = false, catsList = listOf(catBreed)), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        assertEquals("15", underTest.averageLifeSpan.value)
    }

    @Test
    fun `GIVEN empty favourite list of cats in database THEN load no items when view model is initialized`() = runTest {

        // GIVEN
        underTest = FavouritesViewModel(getFavouriteCatsUseCase, updateFavouriteCatUseCase)
        every { getFavouriteCatsUseCase.getFavouriteCats() } returns flowOf(emptyList())

        // THEN
        runCurrent()
        underTest.state.test {
            assertEquals(FavouritesUiState(isLoading = false, catsList = emptyList()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        assertEquals("", underTest.averageLifeSpan.value)
    }


    @Test
    fun `GIVEN cat breed WHEN updateFavourite is called THEN update current cat breed correctly`() = runTest {

        // GIVEN
        underTest = FavouritesViewModel(getFavouriteCatsUseCase, updateFavouriteCatUseCase)
        every { getFavouriteCatsUseCase.getFavouriteCats() } returns flowOf(listOf(catBreed))
        val catBreed1 = CatBreed(
            "id",
            "name",
            "14 - 15",
            "origin",
            "temperament",
            "description",
            "imageId",
            true)

        // WHEN
        underTest.updateFavourite(catBreed1)

        // THEN
        runCurrent()
        coVerify { updateFavouriteCatUseCase.updateFavourite(catBreed1) }
        underTest.state.test {
            assertEquals(FavouritesUiState(isLoading = false, catsList = listOf(catBreed)), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}