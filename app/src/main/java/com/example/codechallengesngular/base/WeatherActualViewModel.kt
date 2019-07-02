package com.example.codechallengesngular.base

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codechallengesngular.R
import com.example.codechallengesngular.model.WeatherActual
import com.example.codechallengesngular.utils.StringResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherActualViewModel : ViewModel() {

    val weatherLiveData = MutableLiveData<WeatherActual>()

    fun loadData() {
        val call: Call<WeatherActual> = openWeatherAPI.actual("London", "0dc43d7a3af12e8343fc23b4be7aa6e2")
        call.enqueue(object : Callback<WeatherActual> {
            override fun onResponse(call: Call<WeatherActual>, response: Response<WeatherActual>) {
                if (response.code() == 200) {
                    val weatherActual = response.body()!!
                    Log.d("weather: ", "$weatherActual")
                    weatherLiveData.value = weatherActual
                } else {
                    Log.d("Lost Connection: ", "break")
                    // TODO Present an user error
                }
            }

            override fun onFailure(call: Call<WeatherActual>, t: Throwable) {
                Log.e("FALLA", t.message)
                // TODO Present an user error
            }
        })
    }
}

sealed class NavigationItem(val tabName: StringResource, @DrawableRes val iconId: Int, val index: Int) {
    object Actual :
        NavigationItem(
            tabName = StringResource(R.string.bottom_nav_actual),
            iconId = R.drawable.ic_actual_grey,
            index = 0
        )

    object Next :
        NavigationItem(
            tabName = StringResource(R.string.bottom_nav_next_days),
            iconId = R.drawable.ic_next_days_grey,
            index = 1
        )


    object Settings :
        NavigationItem(
            tabName = StringResource(R.string.bottom_nav_settings),
            iconId = R.drawable.ic_settings_grey,
            index = 4
        )
}