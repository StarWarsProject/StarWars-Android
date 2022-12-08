package com.example.starwarsapp.ui.movieDetail.viewModel

import android.content.Context
import androidx.lifecycle.Observer
import androidx.test.platform.app.InstrumentationRegistry
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.source.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
    public override fun setUp() {
        super.setUp()
        hiltRule.inject()
        fakeCharacterDataManager = FakeCharacterDataManager()
        fakeMovieDataManager = FakeMovieDataManager()
        fakePlanetDataManager = FakePlanetDataManager()
        fakeSpecieDataManager = FakeSpecieDataManager()
        fakeStarshipDataManager = FakeStarshipDataManager()
        viewModel = MovieDetailViewModel(fakeCharacterDataManager, fakeMovieDataManager, fakePlanetDataManager, fakeSpecieDataManager, fakeStarshipDataManager)
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun `When set selected Movie`() {
        viewModel.setSelectedMovie(1)
        assertEquals(viewModel.selectedMovie.value, FakeData.movie1)
    }

    @Test
    fun `When select Character tab it sets the charactersList with the result`() = runTest {
        // runBlocking() {
        val observer = Observer<List<CharacterEntity>> {}
        try {
            viewModel.charactersList.observeForever(observer)
            viewModel.refreshList(context, FakeData.movie1, TypeTabs.CHARACTERS)
            // assertNotNull(viewModel.charactersList.value)
            // withContext(Dispatchers.Main) {
            val value = viewModel.charactersList.getOrAwaitValue()
            assertNotNull(value)
            // }
            // assertEquals(viewModel.charactersList.value, FakeData.characters)
        } finally {
            viewModel.charactersList.removeObserver(observer)
        }
        // }
    }

    @Test
    fun `When sync Character tab it gets the data locally and sets the charactersList with the result`() {
        viewModel.syncList(context, FakeData.movie1, TypeTabs.CHARACTERS)
        assertEquals(viewModel.charactersList.value, FakeData.characters)
    }

    @Test
    fun `When sync Planet tab it gets the data locally and sets the planetsList with the result`() {
        viewModel.syncList(context, FakeData.movie1, TypeTabs.PLANETS)
        assertEquals(viewModel.planetsList.value, FakeData.planets)
    }

    @Test
    fun `When sync Specie tab it gets the data locally and sets the speciesList with the result`() {
        viewModel.syncList(context, FakeData.movie1, TypeTabs.SPECIES)
        assertEquals(viewModel.speciesList.value, FakeData.species)
    }

    @Test
    fun `When sync StarShips tab it gets the data locally and sets the starshipsList with the result`() {
        viewModel.syncList(context, FakeData.movie1, TypeTabs.SHIPS)
        assertEquals(viewModel.starshipsList.value, FakeData.starships)
    }
}
