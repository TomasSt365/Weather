package com.example.wether.sky.rain.fog.sun.controller

import com.example.wether.sky.rain.fog.sun.R.string.ErrorLoadingWeather
import com.example.wether.sky.rain.fog.sun.data.CityTags
import com.example.wether.sky.rain.fog.sun.data.getCites

class ControllerImpl : Controller {

    override fun getCitesListFromLocalStorage(cityTag: CityTags) = getCites(cityTag)

    override fun errorGettingCitesList() = Throwable("$ErrorLoadingWeather")

}