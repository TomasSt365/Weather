package com.example.wether.sky.rain.fog.sun.data

import android.os.Parcel
import android.os.Parcelable

class Weather() : Parcelable {
    var city: City? = null
    var temperature: Int? = null
    var feelsLike: Int? = null

    constructor(parcel: Parcel) : this() {
        city = parcel.readParcelable(City::class.java.classLoader)
        temperature = parcel.readValue(Int::class.java.classLoader) as? Int
        feelsLike = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    constructor(city: City,
                temperature: Int,
                feelsLike: Int) : this() {
        this.city = city
        this.feelsLike = feelsLike
        this.temperature = temperature
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(city, flags)
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

fun getCites() = listOf<Weather>(
    Weather(City("Moscow"), 12, 13)
)