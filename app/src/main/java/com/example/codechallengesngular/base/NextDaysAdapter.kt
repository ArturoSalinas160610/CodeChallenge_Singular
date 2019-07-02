package com.example.codechallengesngular.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengesngular.R
import com.example.codechallengesngular.model.WeatherNextDays

class NextDaysAdapter(private var dataList: List<WeatherNextDays>, private val context: Context) :
    RecyclerView.Adapter<NextDaysAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_days_weather,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = dataList.get(position)
        holder.day_item_label.text = dataModel.weather.get(position).tempActual
        holder.day_temp_min_label.text = dataModel.weather.get(position).tempMin.toString() + "º"
        holder.day_temp_max_label.text = dataModel.weather.get(position).tempMax.toString() + "º"
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var day_item_label: TextView
        var day_temp_min_label: TextView
        var day_temp_max_label: TextView

        init {
            day_item_label = itemLayoutView.findViewById(R.id.day_item_label)
            day_temp_min_label = itemLayoutView.findViewById(R.id.day_temp_min_label)
            day_temp_max_label = itemLayoutView.findViewById(R.id.day_temp_max_label)
        }
    }
}

