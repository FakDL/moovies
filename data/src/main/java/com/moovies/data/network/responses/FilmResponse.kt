package com.moovies.data.network.responses

import com.moovies.domain.model.Film

data class FilmResponse(
    val id: String,
    val runningTimeInMinutes: Int,
    val image: FilmImage,
    val title: String,
    val titleType: String,
    val year: Int
){
    fun toFilm(): Film = Film(id, title, year, image.url)
}