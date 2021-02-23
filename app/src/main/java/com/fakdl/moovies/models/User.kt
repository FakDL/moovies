package com.fakdl.moovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey
    val email: String,
    val username: String,
    val hashPassword: String
)