package com.example.wether.sky.rain.fog.sun.controller

import android.os.Handler
import android.os.Looper
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
        val key = "X-Yandex-API-Key"
        val value = "4949ae6a-001d-421c-995f-4aca8186b4f0"

        Thread {
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.addRequestProperty(key, value)
            urlConnection.readTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val weatherDTO = Gson().fromJson(reader, WeatherDTO::class.java)
            val handler = Handler(Looper.getMainLooper())
            if (weatherDTO != null) {
                handler.post { listener.onLoaded(weatherDTO) }
            } else {
                val error = Throwable("ERROR!")
                //не смог достучаться до ресурсов
                handler.post { listener.onFailed(error) }
            }
            urlConnection.disconnect()
        }.start()

    }

    private fun getRequestURL(lat: Double, lon: Double) =
        URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")

}