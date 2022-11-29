package com.example.starwarsapp.data.remote.models

import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.utils.ListUtil
import java.util.Date

data class MovieResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Movie>
)

data class Movie(
    val title: String,
    val episode_id: Int,
    val opening_crawl: String,
    val director: String,
    val producer: String,
    val release_date: String,
    val characters: List<String>,
    val planets: List<String>,
    val starships: List<String>,
    val vehicles: List<String>,
    val species: List<String>,
    val created: String,
    val edited: String,
    val url: String
) {
    fun toEntity(): MovieEntity {
        val charactersStr = ListUtil.urlListToJoinedIdString(characters)
        val planetsStr = ListUtil.urlListToJoinedIdString(planets)
        val starshipsStr = ListUtil.urlListToJoinedIdString(starships)
        val vehiclesStr = ListUtil.urlListToJoinedIdString(vehicles)
        val speciesStr = ListUtil.urlListToJoinedIdString(species)
        return MovieEntity(episode_id, title, opening_crawl, director, producer, release_date, charactersStr, planetsStr, starshipsStr, vehiclesStr, speciesStr, Date().time, Date().time)
    }
}
