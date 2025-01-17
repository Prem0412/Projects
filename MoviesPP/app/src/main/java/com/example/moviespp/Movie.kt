package com.example.moviespp



data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val genres: List<String>,
    val director: String,
    val actors: String,
    val plot: String,
    val posterUrl: String
)

data class MovieResponse(
    val results: List<Movie>
)

