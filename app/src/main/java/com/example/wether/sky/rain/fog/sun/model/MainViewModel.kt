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
            val isError = nextBoolean()//todo: строка для теста
            with(liveDataToObserve) {
                if (isError) {
                    Log.d("mylogs", "произошла ошибка загрузки данных с сервера")
                    this.postValue(AppState.Error(controller.errorGettingWeather()))
                } else {
                    Log.d("mylogs", "успешная загрузка данных с сервера")
                    this.postValue(AppState.Success(controller.getWeatherFromLocalStorage(cityTag)))
                }
            }
        }.start()
    }
}