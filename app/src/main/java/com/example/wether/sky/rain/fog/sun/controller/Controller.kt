package com.example.wether.sky.rain.fog.sun.controller
import com.example.wether.sky.rain.fog.sun.data.Weather

interface Controller {
    fun getRemoteResources(): Weather

    fun getLocalResources(): Weather
}