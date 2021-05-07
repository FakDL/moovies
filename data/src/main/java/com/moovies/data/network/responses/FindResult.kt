package com.moovies.data.network.responses

import com.moovies.domain.model.Film

data class FindResult(
    val id: String,
    val legacyNameText: String,
    val name: String,
    val image: FilmImage,
    val runningTimeInMinutes: Int,
    val title: String,
    val titleType: String,
    val year: Int
){
    fun toFilm(): Film = Film(id, title, year, image.url, runningTimeInMinutes)
}