package com.example.starwarsapp.data.source

import com.example.starwarsapp.data.local.models.*

object FakeData {

    var movie1 = MovieEntity(1, 1, "Return of the Jedi", "", "", ", ", "1977-05-25", "", "", "", "", "", 10000, 10000)
    var movie2 = MovieEntity(2, 2, "A new Hope", "", "", ", ", "", "1980-05-17", "", "", "", "", 10000, 10000)
    val movies = listOf(movie1, movie2)
    val newMoviesList = mutableListOf(movie1, movie2)
    var withErrorData = false
}
