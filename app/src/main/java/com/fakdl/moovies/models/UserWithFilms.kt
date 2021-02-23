package com.fakdl.moovies.models

import androidx.room.Embedded
import androidx.room.Relation


data class UserWithFilms (
    @Embedded val user: User,
    @Relation(
        parentColumn = "email",
        entityColumn = "userEmail",
        entity = Film::class
    )
    private val films: List<Film>
)