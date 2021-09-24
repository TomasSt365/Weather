package com.example.wether.sky.rain.fog.sun.controller

import com.example.wether.sky.rain.fog.sun.data.City
import com.example.wether.sky.rain.fog.sun.data.CityTags

interface Controller {
    fun getCitesListFromLocalStorage(cityTag: CityTags): List<City>?
    fun errorGettingCitesList(): Throwable
}