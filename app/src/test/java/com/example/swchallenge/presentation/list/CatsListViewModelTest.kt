package com.example.swchallenge.presentation.list

import app.cash.turbine.test
import com.example.swchallenge.core.DataError
import com.example.swchallenge.core.Resource
import com.example.swchallenge.domain.models.CatBreed
import com.example.swchallenge.domain.usecase.GetCatsUseCase
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
class CatsListViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var getCatsUseCase: GetCatsUseCase
    private lateinit var updateFavouriteCatUseCase: UpdateFavouriteCatUseCase

    private val catBreed1 = CatBreed(
        "id1",
        "name1",
        "14 - 15",
        "origin1",
        "temperament1",
        "description1",
        "imageId1",
        false)

    private val catBreed2 = CatBreed(
        "id2",
        "name2",
        "14 - 15",
        "origin2",
        "temperament2",
        "description2",
        "imageId2",
        false)

    private lateinit var underTest : CatsListViewModel

    @Before
    fun setUp() {
        getCatsUseCase = mockk(relaxUnitFun = true)
        updateFavouriteCatUseCase = mockk(relaxUnitFun = true)
    }

    @Test
    fun `GIVEN list of cats in database THEN load them when view model is initialized`() = runTest {

        // GIVEN
        underTest = CatsListViewModel(getCatsUseCase, updateFavouriteCatUseCase)
        every { getCatsUseCase.getAllCats() } returns flowOf(Resource.Success(listOf(catBreed1, catBreed2)))

        // THEN
        underTest.state.test {
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf()), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf(catBreed1, catBreed2)), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        runCurrent()
        verify { getCatsUseCase.getAllCats() }
    }

    @Test
    fun `GIVEN list of cats in database THEN if its still loading update state`() = runTest {

        // GIVEN
        underTest = CatsListViewModel(getCatsUseCase, updateFavouriteCatUseCase)
        every { getCatsUseCase.getAllCats() } returns flowOf(Resource.Loading())

        // THEN
        underTest.state.test {
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf()), awaitItem())
            assertEquals(CatsListUiState(isLoading = true, isSearching = false, error = null, catsList = listOf()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        runCurrent()
        verify { getCatsUseCase.getAllCats() }
    }

    @Test
    fun `GIVEN list of cats in database THEN if an error occurs update state`() = runTest {

        // GIVEN
        underTest = CatsListViewModel(getCatsUseCase, updateFavouriteCatUseCase)
        every { getCatsUseCase.getAllCats() } returns flowOf(Resource.Error(DataError.Network.PAYLOAD_TOO_LARGE))

        // THEN
        underTest.state.test {
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf()), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = DataError.Network.PAYLOAD_TOO_LARGE, catsList = listOf()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        runCurrent()
        verify { getCatsUseCase.getAllCats() }
    }

    @Test
    fun `GIVEN empty search query WHEN searchCats is called THEN load all cats`() = runTest {

        // GIVEN
        underTest = CatsListViewModel(getCatsUseCase, updateFavouriteCatUseCase)
        every { getCatsUseCase.getAllCats() } returns flowOf(Resource.Success(listOf(catBreed1, catBreed2)))
        val query = ""

        // WHEN
        underTest.searchCats(query)

        // THEN
        assertEquals("", underTest.searchQuery.value)
        underTest.state.test {
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf()), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf(catBreed1, catBreed2)), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        runCurrent()
        verify(exactly = 0) { getCatsUseCase.getCatsByName(query) }
        verify(exactly = 2) { getCatsUseCase.getAllCats() }
    }

    @Test
    fun `GIVEN search query WHEN searchCats is called THEN load cats with query`() = runTest {

        // GIVEN
        underTest = CatsListViewModel(getCatsUseCase, updateFavouriteCatUseCase)
        every { getCatsUseCase.getAllCats() } returns flowOf(Resource.Success(listOf(catBreed1, catBreed2)))
        val query = "name2"
        every { getCatsUseCase.getCatsByName(query) } returns flowOf(Resource.Success(listOf(catBreed2)))

        // WHEN
        underTest.searchCats(query)

        // THEN
        assertEquals("name2", underTest.searchQuery.value)
        underTest.state.test {
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf()), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf(catBreed1, catBreed2)), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = true, error = null, catsList = listOf(catBreed1, catBreed2)), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = true, error = null, catsList = listOf(catBreed2)), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        runCurrent()
        verify { getCatsUseCase.getCatsByName(query) }
        verify(exactly = 1) { getCatsUseCase.getAllCats() }
    }

    @Test
    fun `GIVEN search query WHEN searchCats is called THEN job is loading`() = runTest {

        // GIVEN
        underTest = CatsListViewModel(getCatsUseCase, updateFavouriteCatUseCase)
        every { getCatsUseCase.getAllCats() } returns flowOf(Resource.Success(listOf(catBreed1, catBreed2)))
        val query = "name2"
        every { getCatsUseCase.getCatsByName(query) } returns flowOf(Resource.Loading())

        // WHEN
        underTest.searchCats(query)

        // THEN
        assertEquals("name2", underTest.searchQuery.value)
        underTest.state.test {
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf()), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf(catBreed1, catBreed2)), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = true, error = null, catsList = listOf(catBreed1, catBreed2)), awaitItem())
            assertEquals(CatsListUiState(isLoading = true, isSearching = true, error = null, catsList = listOf()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        runCurrent()
        verify { getCatsUseCase.getCatsByName(query) }
        verify(exactly = 1) { getCatsUseCase.getAllCats() }
    }

    @Test
    fun `GIVEN search query WHEN searchCats is called THEN error occurs`() = runTest {

        // GIVEN
        underTest = CatsListViewModel(getCatsUseCase, updateFavouriteCatUseCase)
        every { getCatsUseCase.getAllCats() } returns flowOf(Resource.Success(listOf(catBreed1, catBreed2)))
        val query = "name2"
        every { getCatsUseCase.getCatsByName(query) } returns flowOf(Resource.Error(DataError.Network.REQUEST_TIMEOUT))

        // WHEN
        underTest.searchCats(query)

        // THEN
        assertEquals("name2", underTest.searchQuery.value)
        underTest.state.test {
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf()), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf(catBreed1, catBreed2)), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = true, error = null, catsList = listOf(catBreed1, catBreed2)), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = true, error = DataError.Network.REQUEST_TIMEOUT, catsList = listOf()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        runCurrent()
        verify { getCatsUseCase.getCatsByName(query) }
        verify(exactly = 1) { getCatsUseCase.getAllCats() }
    }

    @Test
    fun `GIVEN cat breed WHEN updateFavourite is called THEN update current cat breed correctly`() = runTest {

        // GIVEN
        underTest = CatsListViewModel(getCatsUseCase, updateFavouriteCatUseCase)
        every { getCatsUseCase.getAllCats() } returns flowOf(Resource.Success(listOf(catBreed1, catBreed2)))
        val catBreedFavourite = CatBreed(
            "id",
            "name",
            "14 - 15",
            "origin",
            "temperament",
            "description",
            "imageId",
            true)

        // WHEN
        underTest.updateFavourite(catBreedFavourite)

        // THEN
        underTest.state.test {
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf()), awaitItem())
            assertEquals(CatsListUiState(isLoading = false, isSearching = false, error = null, catsList = listOf(catBreed1, catBreed2)), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        runCurrent()
        coVerify { updateFavouriteCatUseCase.updateFavourite(catBreedFavourite) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}