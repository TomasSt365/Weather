package com.example.wether.sky.rain.fog.sun.view.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wether.sky.rain.fog.sun.R
import com.example.wether.sky.rain.fog.sun.data.Weather

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.MainFragmentViewHolder>() {

    private var weatherData: List<Weather> = listOf()
    private var isDataSetRus: Boolean = true


    /*private var _biding: ItemListElementBinding? = null
    private val binding: ItemListElementBinding
        get() = _biding!!*/

    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        return MainFragmentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.render(weatherData[position])
    }

    override fun getItemCount() = weatherData.size

    inner class MainFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun render(weather: Weather) {
            itemView.findViewById<TextView>(R.id.cityName).text =
                weather.city?.name ?: ""
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        //_biding = null
    }
}