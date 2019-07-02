package com.example.codechallengesngular.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.example.codechallengesngular.R
import com.example.codechallengesngular.api.OpenWeatherAPI
import com.example.codechallengesngular.model.Main
import com.example.codechallengesngular.model.MainDays
import com.example.codechallengesngular.model.Weather
import com.example.codechallengesngular.model.WeatherActual
import com.example.codechallengesngular.model.WeatherNextDays

val openWeatherAPI by lazy {
    OpenWeatherAPI.create()
}

class WeatherNextDaysFragment : Fragment() {

    private lateinit var viewModel: WeatherNextDaysViewModel

    companion object {
        fun newInstance() = WeatherNextDaysFragment()
    }

    lateinit var adapter: NextDaysAdapter
    var dataListDays = ArrayList<WeatherNextDays>()
    lateinit var recyclerView: RecyclerView
    var weather= ArrayList<MainDays>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_next_days_weather, container, false)
        ButterKnife.bind(this, rootView)
        return rootView
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        weather.add(0, MainDays("Tuesday",26.45F, 34.4F))
        weather.add(1, MainDays("Wednesday",20.45F, 44.4F))
        weather.add(2, MainDays("Thursday",16.45F, 36.4F))
        weather.add(3, MainDays("Friday",19.45F, 37.4F))
        weather.add(4, MainDays("Saturday",20.45F, 38.4F))

        dataListDays.add(0, WeatherNextDays(weather))
        dataListDays.add(1, WeatherNextDays(weather))
        dataListDays.add(2, WeatherNextDays(weather))
        dataListDays.add(3, WeatherNextDays(weather))
        dataListDays.add(4, WeatherNextDays(weather))

        recyclerView = view!!.findViewById(R.id.weather_nex_days_list)
        recyclerView.adapter = NextDaysAdapter(dataListDays, this.requireContext())
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)

        //viewModel = ViewModelProviders.of(this).get(WeatherNextDaysViewModel::class.java)
        //viewModel.loadData()
        //viewModel.weatherNextDaysLiveData.observe(this, Observer {
        //TODO
        //})
    }
}