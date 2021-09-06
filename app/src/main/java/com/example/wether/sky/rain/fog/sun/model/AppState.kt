package com.example.wether.sky.rain.fog.sun.model

import com.example.wether.sky.rain.fog.sun.data.Weather

sealed class AppState {
    object Loading : AppState()
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
}
