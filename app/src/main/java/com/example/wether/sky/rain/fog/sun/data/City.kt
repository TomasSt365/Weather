package com.example.wether.sky.rain.fog.sun.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class City(val name: String, private val lat: Double, private val lon: Double) : Parcelable