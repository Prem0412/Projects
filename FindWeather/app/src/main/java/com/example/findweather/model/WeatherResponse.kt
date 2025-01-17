package com.example.findweather.model

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

