package com.example.codechallengesngular.model

import com.google.gson.annotations.SerializedName

data class WeatherNextDays(
    val weather: List<MainDays>
) {
    override fun toString(): String {
        return "WeatherActual(weather=$weather)"
    }
}

data class MainDays(
    @SerializedName("day")
    val tempActual: String,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float
)