package com.example.wether.sky.rain.fog.sun.controller

import com.example.wether.sky.rain.fog.sun.data.Weather
import com.example.wether.sky.rain.fog.sun.data.getCites

class ControllerImpl : Controller {

    override fun getWeatherFromRemoteSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorage(): List<Weather> {
        return getCites()
    }

}