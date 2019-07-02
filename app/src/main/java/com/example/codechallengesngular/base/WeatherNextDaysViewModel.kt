package com.example.codechallengesngular.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codechallengesngular.model.WeatherNextDays
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherNextDaysViewModel : ViewModel() {

    val weatherNextDaysLiveData = MutableLiveData<WeatherNextDays>()

    fun loadData(city: String) {
        val call: Call<WeatherNextDays> = openWeatherAPI.dailyForecast(city, "0dc43d7a3af12e8343fc23b4be7aa6e2", 5)
        call.enqueue(object : Callback<WeatherNextDays> {
            override fun onResponse(call: Call<WeatherNextDays>, response: Response<WeatherNextDays>) {
                val weatherNextDays = response.body()!!
                Log.d("weather: ", "$weatherNextDays")
                weatherNextDaysLiveData.value = weatherNextDays
            }
            override fun onFailure(call: Call<WeatherNextDays>, t: Throwable) {
                Log.e("ERROR", t.message)
            }
        })
    }
}