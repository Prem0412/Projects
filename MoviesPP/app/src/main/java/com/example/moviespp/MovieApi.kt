package com.example.moviespp



import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {
    @GET("movies")
    suspend fun getMovies(): Response<List<Movie>>
}