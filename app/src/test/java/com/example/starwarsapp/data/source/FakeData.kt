package com.example.starwarsapp.data.source

import com.example.starwarsapp.data.local.models.*
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.data.remote.models.Planet
import com.example.starwarsapp.data.remote.models.Specie
import com.example.starwarsapp.data.remote.models.Starship

object FakeData {

    var movie1 = MovieEntity(1, 1, "Return of the Jedi", "", "", ", ", "1977-05-25", "", "", "", "", "", 10000, 10000)
    var movie2 = MovieEntity(2, 2, "A new Hope", "", "", ", ", "", "1980-05-17", "", "", "", "", 10000, 10000)
    val movies = listOf(movie1, movie2)
    val newMoviesList = mutableListOf<MovieEntity>(movie1, movie2)
    var withData = 0

    private val character1 = CharacterEntity(1, "Luke Skywalker", "", "", "", "", "", "", "", 10000, 10000)
    private val character2 = CharacterEntity(2, "R2-D2", "", "", "", "", "", "", "", 10000, 10000)
    val characters = listOf(character1, character2)
    private val listStrings = listOf("", "")
    private val characterResponse1 = People("", "", listStrings, "", "", "", "", "", "Luke Skywalker", "", "", "", listStrings, listStrings, "https://swapi.dev/api/people/1/", listStrings)
    private val characterResponse2 = People("", "", listStrings, "", "", "", "", "", "R2-D2", "", "", "", listStrings, listStrings, "https://swapi.dev/api/people/2/", listStrings)
    val charactersResponse = listOf(characterResponse1, characterResponse2)

    private val planet1 = PlanetEntity(1, "Tatooine", "", "", "", "", "", "", "", 10000, 10000)
    private val planet2 = PlanetEntity(2, "Alderaan", "", "", "", "", "", "", "", 10000, 10000)
    val planets = listOf(planet1, planet2)
    private val planetResponse1 = Planet("Tatooine", "", "", "", "", "", "", "", "", listStrings, listStrings, "", "", "https://swapi.dev/api/planets/1/")
    private val planetResponse2 = Planet("Alderaan", "", "", "", "", "", "", "", "", listStrings, listStrings, "", "", "https://swapi.dev/api/planets/2/")
    val planetsResponse = listOf(planetResponse1, planetResponse2)

    private val specie1 = SpecieEntity(1, "Human", "", "", "", "", "", 10000, 10000)
    private val specie2 = SpecieEntity(2, "Droid", "", "", "", "", "", 10000, 10000)
    val species = listOf(specie1, specie2)
    private val specieResponse1 = Specie("", "", "", "", "", "", "", listStrings, "", "", "", "Human", listStrings, "", "https://swapi.dev/api/species/1/")
    private val specieResponse2 = Specie("", "", "", "", "", "", "", listStrings, "", "", "", "Droid", listStrings, "", "https://swapi.dev/api/species/2/")
    val specieResponse = listOf(specieResponse1, specieResponse2)

    private val starship1 = StarshipEntity(1, "Death Star", "", "", "", "", "", "", "", "", 10000, 10000)
    private val starship2 = StarshipEntity(2, "Millennium Falcon", "", "", "", "", "", "", "", "", 10000, 10000)
    val starships = listOf(starship1, starship2)
    private val startshipResponse1 = Starship("", "", "", "", "", "", "", listStrings, "", "", "", "", "", "", "", listStrings, "Death Star", "https://swapi.dev/api/starships/1/")
    private val startshipResponse2 = Starship("", "", "", "", "", "", "", listStrings, "", "", "", "", "", "", "", listStrings, "Millennium Falcon", "https://swapi.dev/api/starships/2/")
    val starshipsResponse = listOf(startshipResponse1, startshipResponse2)
}
