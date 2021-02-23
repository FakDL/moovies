package com.fakdl.moovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "film_table")
data class Film(
    @PrimaryKey val filmId: Long,
    val userEmail: String
)