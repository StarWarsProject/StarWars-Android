package com.example.starwarsapp.ui.home.viewModel

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.source.FakeData
import com.example.starwarsapp.data.source.FakeMovieDataManager
import com.example.starwarsapp.data.source.FakeSwapiRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.junit.jupiter.api.Assertions.*

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, manifest = Config.NONE)
@HiltAndroidTest
class MoviesListViewModelTest : TestCase() {
    @get :Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: MoviesListViewModel
    private lateinit var fakeMovieDataManager: FakeMovieDataManager
    private lateinit var fakeSwapiManager: SwapiRepository
    private lateinit var context: Context

    @Before
    public override fun setUp() {
        super.setUp()
        hiltRule.inject()
        fakeSwapiManager = FakeSwapiRepository()
        fakeMovieDataManager = FakeMovieDataManager()
        viewModel = MoviesListViewModel(fakeMovieDataManager)
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun `Get all movies and show data if not null`() = kotlinx.coroutines.test.runTest {
        viewModel.getAllMovies(context)
        viewModel.moviesList.value?.let { assertEquals(7, it.size) }
        // assertEquals(viewModel.moviesList.value, FakeData.movies)
        assertEquals(viewModel.activeMovie.value, FakeData.movie1)
    }

    @Test
    fun `Filter the movies list by release date`() {
        viewModel.moviesList.value = FakeData.movies
        viewModel.filterByReleaseDate()
        FakeData.newMoviesList.sortBy { it.releaseDate }
        assertEquals(FakeData.newMoviesList, viewModel.moviesList.value)
    }

    @Test
    fun `Filter the movies list by history`() {
        viewModel.moviesList.value = FakeData.movies
        viewModel.filterByHistory()
        FakeData.newMoviesList.sortBy { it.episode_id }
        assertEquals(FakeData.newMoviesList, viewModel.moviesList.value)
    }

    @Test
    fun `Update active Movie`() {
        viewModel.updateActiveMovie(FakeData.movie1)
        assertEquals(FakeData.movie1, viewModel.activeMovie.value)
    }
}
