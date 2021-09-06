package com.example.wether.sky.rain.fog.sun.controller

import com.example.wether.sky.rain.fog.sun.data.Weather
import com.example.wether.sky.rain.fog.sun.data.City

interface Controller {
    fun getWeatherFromRemoteSource(): Weather
    fun getWeatherFromLocalSource(): Weather

    fun getWeatherFromLocalStorage(): List<Weather>
}