package com.example.starwarsapp.ui.movieDetail.viewModel

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.starwarsapp.data.source.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, manifest = Config.NONE)
@HiltAndroidTest
class MovieDetailViewModelTest : TestCase() {
    @get :Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var fakeCharacterDataManager: FakeCharacterDataManager
    private lateinit var fakeMovieDataManager: FakeMovieDataManager
    private lateinit var fakePlanetDataManager: FakePlanetDataManager
    private lateinit var fakeSpecieDataManager: FakeSpecieDataManager
    private lateinit var fakeStarshipDataManager: FakeStarshipDataManager
    private lateinit var context: Context

    @Before
    public override fun setUp() = runTest {
        super.setUp()
        hiltRule.inject()
        val testDispatcherIO = UnconfinedTestDispatcher(testScheduler)
        val testDispatcherMain = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcherMain)
        fakeCharacterDataManager = FakeCharacterDataManager()
        fakeMovieDataManager = FakeMovieDataManager()
        fakePlanetDataManager = FakePlanetDataManager()
        fakeSpecieDataManager = FakeSpecieDataManager()
        fakeStarshipDataManager = FakeStarshipDataManager()
        viewModel = MovieDetailViewModel(fakeCharacterDataManager, fakeMovieDataManager, fakePlanetDataManager, fakeSpecieDataManager, fakeStarshipDataManager, testDispatcherIO, testDispatcherMain)
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun `When set selected Movie`() {
        viewModel.setSelectedMovie(1)
        assertEquals(viewModel.selectedMovie.value, FakeData.movie1)
    }

    @Test
    fun `When refresh Character tab it gets the data from internet and save it, then sets the charactersList with the result (SUCCESS RESULT)`() = runTest {
        try {
            FakeData.withData = 1
            viewModel.refreshList(context, FakeData.movie1, TypeTabs.CHARACTERS)
            viewModel.charactersList.value?.let { assertEquals(1, it.size) }
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `When refresh Character tab it gets the data from internet and save it, then sets the charactersList with the result (FAILURE RESULT)`() = runTest {
        try {
            FakeData.withData = 0
            viewModel.refreshList(context, FakeData.movie1, TypeTabs.CHARACTERS)
            viewModel.charactersList.value?.let { assertEquals(0, it.size) }
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `When refresh Planet tab it gets the data from internet and save it, then sets the planetsList with the result (SUCCESS RESULT)`() = runTest {
        try {
            FakeData.withData = 1
            viewModel.refreshList(context, FakeData.movie1, TypeTabs.PLANETS)
            viewModel.planetsList.value?.let { assertEquals(1, it.size) }
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `When refresh Planet tab it gets the data from internet and save it, then sets the planetsList with the result (FAILURE RESULT)`() = runTest {
        try {
            FakeData.withData = 0
            viewModel.refreshList(context, FakeData.movie1, TypeTabs.PLANETS)
            viewModel.charactersList.value?.let { assertEquals(0, it.size) }
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `When refresh Specie tab it gets the data from internet and save it, then sets the speciesList with the result (SUCCESS RESULT)`() = runTest {
        try {
            FakeData.withData = 1
            viewModel.refreshList(context, FakeData.movie1, TypeTabs.SPECIES)
            viewModel.speciesList.value?.let { assertEquals(1, it.size) }
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `When refresh Specie tab it gets the data from internet and save it, then sets the speciesList with the result (FAILURE RESULT)`() = runTest {
        try {
            FakeData.withData = 0
            viewModel.refreshList(context, FakeData.movie1, TypeTabs.SPECIES)
            viewModel.speciesList.value?.let { assertEquals(0, it.size) }
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `When refresh Starship tab it gets the data from internet and save it, then sets the starshipsList with the result (SUCCESS RESULT)`() = runTest {
        try {
            FakeData.withData = 1
            viewModel.refreshList(context, FakeData.movie1, TypeTabs.SHIPS)
            viewModel.starshipsList.value?.let { assertEquals(1, it.size) }
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `When refresh Starship tab it gets the data from internet and save it, then sets the starshipsList with the result (FAILURE RESULT)`() = runTest {
        try {
            FakeData.withData = 0
            viewModel.refreshList(context, FakeData.movie1, TypeTabs.SHIPS)
            viewModel.starshipsList.value?.let { assertEquals(0, it.size) }
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `When sync Character tab it gets the data locally and sets the charactersList with the result (SUCCESS RESULT)`() {
        FakeData.withData = 1
        viewModel.syncList(context, FakeData.movie1, TypeTabs.CHARACTERS)
        viewModel.charactersList.value?.let { assertEquals(1, it.size) }
    }

    @Test
    fun `When sync Character tab it gets the data locally and sets the charactersList with the result (FAILURE RESULT)`() {
        FakeData.withData = 0
        viewModel.syncList(context, FakeData.movie1, TypeTabs.CHARACTERS)
        viewModel.charactersList.value?.let { assertEquals(0, it.size) }
    }

    @Test
    fun `When sync Planet tab it gets the data locally and sets the planetsList with the result (SUCCESS RESULT)`() {
        FakeData.withData = 1
        viewModel.syncList(context, FakeData.movie1, TypeTabs.PLANETS)
        viewModel.planetsList.value?.let { assertEquals(1, it.size) }
    }

    fun `When sync Planet tab it gets the data locally and sets the planetsList with the result (FAILURE RESULT)`() {
        FakeData.withData = 0
        viewModel.syncList(context, FakeData.movie1, TypeTabs.PLANETS)
        viewModel.planetsList.value?.let { assertEquals(0, it.size) }
    }

    @Test
    fun `When sync Specie tab it gets the data locally and sets the speciesList with the result (SUCCESS RESULT)`() {
        FakeData.withData = 1
        viewModel.syncList(context, FakeData.movie1, TypeTabs.SPECIES)
        viewModel.speciesList.value?.let { assertEquals(1, it.size) }
    }

    @Test
    fun `When sync Specie tab it gets the data locally and sets the speciesList with the result (FAILURE RESULT)`() {
        FakeData.withData = 0
        viewModel.syncList(context, FakeData.movie1, TypeTabs.SPECIES)
        viewModel.speciesList.value?.let { assertEquals(0, it.size) }
    }

    @Test
    fun `When sync StarShips tab it gets the data locally and sets the starshipsList with the result (SUCCESS RESULT)`() {
        FakeData.withData = 1
        viewModel.syncList(context, FakeData.movie1, TypeTabs.SHIPS)
        viewModel.starshipsList.value?.let { assertEquals(1, it.size) }
    }

    @Test
    fun `When sync StarShips tab it gets the data locally and sets the starshipsList with the result (FAILURE RESULT)`() {
        FakeData.withData = 0
        viewModel.syncList(context, FakeData.movie1, TypeTabs.SHIPS)
        viewModel.starshipsList.value?.let { assertEquals(0, it.size) }
    }
}
