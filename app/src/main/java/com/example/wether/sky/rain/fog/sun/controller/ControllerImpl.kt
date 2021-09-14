package com.example.wether.sky.rain.fog.sun.controller

import com.example.wether.sky.rain.fog.sun.data.Weather
import com.example.wether.sky.rain.fog.sun.data.getCites
import com.example.wether.sky.rain.fog.sun.data.CityTags

class ControllerImpl : Controller {

    override fun getWeatherFromLocalStorage(cityTag: CityTags): List<Weather> {
        return getCites(cityTag)
    }

}