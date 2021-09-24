package com.example.wether.sky.rain.fog.sun.controller

interface WeatherLoaderListener {
    fun onLoaded(weatherDTO: WeatherDTO)
    fun onFailed(errors: List<String>?)
}