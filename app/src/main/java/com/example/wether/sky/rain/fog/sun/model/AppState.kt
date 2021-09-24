package com.example.wether.sky.rain.fog.sun.model

import com.example.wether.sky.rain.fog.sun.data.City

sealed class AppState {
    object Loading : AppState()
    data class Success(val citesData: List<City>) : AppState()
    data class Error(val error: Throwable) : AppState()
}
