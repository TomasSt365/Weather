package com.example.wether.sky.rain.fog.sun.controller

data class FactDTO(
    val obs_time: Int?,
    val temp: Int?,
    val feels_like: Int?,
    val icon: String?,
    val condition: String?,
    val wind_speed: Int?,
    val wind_dir: String?,
    val pressure_mm: Int?,
    val pressure_pa: Int?,
    val humidity: Int?,
    val daytime: String?,
    val polar: Boolean?,
    val season: String?,
    val wind_gust: Double?,
    val errors: List<String>?
    //todo: свойство из конвертированого Json с ошибками. Проверить на коректную работу!
)


