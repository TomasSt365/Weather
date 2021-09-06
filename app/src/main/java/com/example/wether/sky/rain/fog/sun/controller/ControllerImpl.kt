package com.example.wether.sky.rain.fog.sun.controller

import com.example.wether.sky.rain.fog.sun.data.Weather

class ControllerImpl : Controller {

    override fun getRemoteResources(): Weather {
        return Weather()
    }

    override fun getLocalResources(): Weather {
        return Weather()
    }

}