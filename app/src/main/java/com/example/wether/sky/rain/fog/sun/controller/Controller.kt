package com.example.wether.sky.rain.fog.sun.controller

import com.example.wether.sky.rain.fog.sun.data.CityTags
import com.example.wether.sky.rain.fog.sun.data.Weather

interface Controller {
    fun getWeatherFromLocalStorage(cityTag: CityTags): List<Weather>
}