package com.moovies.domain.model

data class Film (
    val id: String,
    val title: String,
    val year: Int,
    val imgUrl: String,
    val duration: Int
)