package com.example.codechallengesngular.api

import com.example.codechallengesngular.model.WeatherActual
import com.example.codechallengesngular.model.WeatherNextDays
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {

    @GET("weather")
    fun actual(
        @Query("q") cityName: String,
        @Query("APPID") id: String
    ): Call<WeatherActual>

    @GET("forecast/daily")
    fun dailyForecast(
        @Query("q") cityName: String,
        @Query("APPID") id: String,
        @Query("cnt") dayCount: Int
    ): Call<WeatherNextDays>

    companion object {
        private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        fun create(): OpenWeatherAPI {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return requestInterface.create(OpenWeatherAPI::class.java)
        }
    }
}