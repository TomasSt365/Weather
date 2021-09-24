package com.example.wether.sky.rain.fog.sun.controller

import android.os.Handler
import android.os.Looper
import com.example.wether.sky.rain.fog.sun.data.WeatherDTO
import com.example.wether.sky.rain.fog.sun.data.FactDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(
    private val listener: WeatherLoaderListener,
    private val lat: Double,
    private val lon: Double
) {

    fun loadWeather() {

        val url = getRequestURL(lat, lon)
        val APIkeys = HashMap<String, String>()
        val yandexWeatherKey = "X-Yandex-API-Key"
        APIkeys[yandexWeatherKey] = "4949ae6a-001d-421c-995f-4aca8186b4f0"

        Thread {
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            with(yandexWeatherKey){
                urlConnection.addRequestProperty(this, APIkeys[this])
            }
            urlConnection.readTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val factDTO = Gson().fromJson(reader, FactDTO::class.java)
            val weatherDTO = WeatherDTO(factDTO)
            val handler = Handler(Looper.getMainLooper())
            handler.post { listener.onLoaded(weatherDTO) }
            urlConnection.disconnect()
        }.start()

    }

    private fun getRequestURL(lat: Double, lon: Double): URL {
        val protocol = "https"
        val host = "api.weather.yandex.ru"
        val path = "v2/informers"
        val request = "?lat=${lat}&lon=${lon}"

        return URL("${protocol}://${host}/${path}${request}")
    }
}