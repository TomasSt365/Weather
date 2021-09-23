package com.example.wether.sky.rain.fog.sun.controller

import com.example.wether.sky.rain.fog.sun.data.CityTags
import com.example.wether.sky.rain.fog.sun.data.getCites

class ControllerImpl : Controller {

    override fun getWeatherFromLocalStorage(cityTag: CityTags) = getCites(cityTag)

    override fun errorGettingWeather() =
        Throwable("failed to get a response from the server")//не смог достучаться до ресурсов

}