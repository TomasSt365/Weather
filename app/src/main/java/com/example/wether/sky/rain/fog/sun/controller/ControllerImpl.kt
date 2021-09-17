package com.example.wether.sky.rain.fog.sun.controller

import com.example.wether.sky.rain.fog.sun.data.Weather
import com.example.wether.sky.rain.fog.sun.data.getCites
import com.example.wether.sky.rain.fog.sun.data.CityTags

class ControllerImpl : Controller {

    override fun getWeatherFromLocalStorage(cityTag: CityTags) = getCites(cityTag)

    override fun errorGettingWeather() =
        Throwable("failed to get a response from the server")//не смог достучаться до ресурсов

}