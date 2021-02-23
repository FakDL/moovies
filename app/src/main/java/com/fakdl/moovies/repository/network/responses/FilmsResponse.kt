package com.fakdl.moovies.repository.network.responses

data class FilmsResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)