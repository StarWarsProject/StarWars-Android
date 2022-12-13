package com.example.starwarsapp.ui.home.viewModel

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.starwarsapp.data.source.FakeData
import com.example.starwarsapp.data.source.FakeMovieDataManager
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MoviesListViewModelTest : TestCase() {
    @get :Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: MoviesListViewModel
    private lateinit var fakeMovieDataManager: FakeMovieDataManager
    private lateinit var context: Context

    @Before
    public override fun setUp() {
        super.setUp()
        hiltRule.inject()
        fakeMovieDataManager = FakeMovieDataManager()
        viewModel = MoviesListViewModel(fakeMovieDataManager)
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun `Get all movies and show data if not null`() = kotlinx.coroutines.test.runTest {
        FakeData.withErrorData = false
        viewModel.getAllMovies(context)
        viewModel.moviesList.value?.let { assertEquals(7, it.size) }
        assertNotNull(viewModel.activeMovie.value)
    }

    @Test
    fun `Get all movies but if api data fails will not set the movies list`() = kotlinx.coroutines.test.runTest {
        FakeData.withErrorData = true
        viewModel.getAllMovies(context)
        viewModel.moviesList.value?.let { assertEquals(0, it.size) }
        assertNull(viewModel.activeMovie.value)
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
