package com.example.wether.sky.rain.fog.sun.controller

data class FactDTO(
    val obs_time: Int,
    var temp: Int,
    var feels_like: Int,
    var icon: String,
    var condition: String,
    var wind_speed: Int,
    var wind_dir: String,
    var pressure_mm: Int,
    var pressure_pa: Int,
    var humidity: Int,
    var daytime: String,
    var polar: Boolean,
    var season: String,
    var wind_gust: Double
)


