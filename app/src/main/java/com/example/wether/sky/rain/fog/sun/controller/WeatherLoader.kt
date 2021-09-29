package com.example.wether.sky.rain.fog.sun.controller

import Const.APIkeys
import Const.yandexWeatherKey
import android.os.Handler
import android.os.Looper
import com.example.wether.sky.rain.fog.sun.R
import com.example.wether.sky.rain.fog.sun.data.WeatherDTO
import com.google.gson.Gson
import getWeatherULR
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(
    private val listener: WeatherLoaderListener,
    private val lat: Double,
    private val lon: Double
) {

    fun loadWeather() {

        val url = getWeatherULR(lat, lon)

        Thread {
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            with(yandexWeatherKey){
                urlConnection.addRequestProperty(this, APIkeys[this])
            }
            urlConnection.readTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val weatherDTO  = Gson().fromJson(reader, WeatherDTO::class.java)
            val handler = Handler(Looper.getMainLooper())
            if (weatherDTO != null) {
                if (weatherDTO.fact.errors != null) {
                    val errors = weatherDTO.fact.errors
                    handler.post { listener.onFailed(errors) }
                }
                handler.post { listener.onLoaded(weatherDTO) }
            } else {
                val errors = listOf("${R.string.ServerErrorMessage}")
                handler.post { listener.onFailed(errors) }
            }
            urlConnection.disconnect()
        }.start()

    }
}