package com.example.wether.sky.rain.fog.sun.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wether.sky.rain.fog.sun.controller.ControllerImpl
import com.example.wether.sky.rain.fog.sun.data.CityTags
import java.lang.Thread.sleep
import kotlin.random.Random.Default.nextBoolean

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val controller: ControllerImpl = ControllerImpl(),
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSource(cityTag: CityTags) {
        getDataFromLocalSource(cityTag)
    }

    private fun getDataFromLocalSource(cityTag: CityTags) {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(1000)
            with(liveDataToObserve) {
                controller.also {
                        Log.d("mylogs", "успешная загрузка данных с сервера")
                        postValue(AppState.Success(it.getWeatherFromLocalStorage(cityTag)))
                }
            }
        }.start()
    }
}