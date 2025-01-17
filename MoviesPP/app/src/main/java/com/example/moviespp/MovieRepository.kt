package com.example.moviespp



import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {
    private val api: MovieApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetestapi.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(MovieApi::class.java)
    }

    suspend fun getMovies() = api.getMovies()
}
