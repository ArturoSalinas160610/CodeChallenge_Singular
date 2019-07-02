package com.example.codechallengesngular.model

import com.google.gson.annotations.SerializedName

data class WeatherActual(
    val weather: List<Weather>,
    val main: Main
) {
    override fun toString(): String {
        return "WeatherActual(weather=$weather, main=$main)"
    }
}

data class Weather(
    val main: String
)

data class Main(
    @SerializedName("temp")
    val tempActual: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float
)