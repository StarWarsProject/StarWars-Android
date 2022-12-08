package com.example.starwarsapp.data.source

import com.example.starwarsapp.data.local.models.*

object FakeData {

    var movie1 = MovieEntity(1, "Return of the Jedi", "", "", ", ", "", "", "", "", "", "", 10000, 10000)
    var movie2 = MovieEntity(2, "A new Hope", "", "", ", ", "", "", "", "", "", "", 10000, 10000)
    val movies = listOf(movie1, movie2)

    val character1 = CharacterEntity(1, "Luke Skywalker", "", "", "", "", "", "", "", 10000, 10000)
    val character2 = CharacterEntity(2, "R2-D2", "", "", "", "", "", "", "", 10000, 10000)
    val characters = listOf(character1, character2)

    val planet1 = PlanetEntity(1, "Tatooine", "", "", "", "", "", "", "", 10000, 10000)
    val planet2 = PlanetEntity(2, "Alderaan", "", "", "", "", "", "", "", 10000, 10000)
    val planets = listOf(planet1, planet2)

    val specie1 = SpecieEntity(1, "Human", "", "", "", "", "", 10000, 10000)
    val specie2 = SpecieEntity(2, "Droid", "", "", "", "", "", 10000, 10000)
    val species = listOf(specie1, specie2)

    val starship1 = StarshipEntity(1, "Death Star", "", "", "", "", "", "", "", "", 10000, 10000)
    val starship2 = StarshipEntity(2, "Millennium Falcon", "", "", "", "", "", "", "", "", 10000, 10000)
    val starships = listOf(starship1, starship2)
}
