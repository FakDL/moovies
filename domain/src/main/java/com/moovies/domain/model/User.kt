package com.moovies.domain.model


data class User (
    val email: String,
    val username: String,
    val hashPassword: String
)