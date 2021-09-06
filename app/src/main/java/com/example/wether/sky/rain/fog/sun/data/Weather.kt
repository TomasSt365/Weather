package com.example.wether.sky.rain.fog.sun.data

import android.os.Parcel
import android.os.Parcelable

class Weather() : Parcelable {
    var temperature: Int? = null
    var feelsLike: Int? = null

    constructor(parcel: Parcel) : this() {
        temperature = parcel.readValue(Int::class.java.classLoader) as? Int
        feelsLike = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(temperature)
        parcel.writeValue(feelsLike)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}

fun getDefaultCity() = City("Moscow")