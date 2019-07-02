package com.example.codechallengesngular.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.example.codechallengesngular.R

class WeatherActualFragment : Fragment() {
    companion object {
        fun newInstance(city: String): WeatherActualFragment {
            val args = Bundle()
            args.putString(ACTUAL_CITY, city)
            val fragment = WeatherActualFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var actualViewModel: WeatherActualViewModel
    lateinit var currentCity: String

    @BindView(R.id.temp_min)
    protected lateinit var tempMin: TextView
    @BindView(R.id.temp_max)
    protected lateinit var tempMax: TextView
    @BindView(R.id.icon_sky)
    protected lateinit var iconSky: ImageView
    @BindView(R.id.sky)
    protected lateinit var sky: TextView
    @BindView(R.id.temperature)
    protected lateinit var temperature: TextView
    @BindView(R.id.city)
    protected lateinit var cityLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null)
            currentCity = arguments!!.getString(ACTUAL_CITY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_actual_weather, container, false)
        ButterKnife.bind(this, rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityLabel.text = currentCity
    }

    override fun onResume() {
        super.onResume()
        actualViewModel.loadData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actualViewModel = ViewModelProviders.of(this).get(WeatherActualViewModel::class.java)
        actualViewModel.loadData()
        actualViewModel.weatherLiveData.observe(this, Observer {
            if (it.weather.isNotEmpty()) {
                sky.text = it.weather[0].main
            }
            temperature.text = "${it.main.tempActual}º"
            tempMin.text = "${it.main.tempMin}º"
            tempMax.text = "${it.main.tempMax}º"
        })
    }
}

private const val ACTUAL_CITY = "actual_city"