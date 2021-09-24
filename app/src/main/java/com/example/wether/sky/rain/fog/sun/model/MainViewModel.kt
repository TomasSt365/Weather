package com.example.wether.sky.rain.fog.sun.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wether.sky.rain.fog.sun.controller.ControllerImpl
import com.example.wether.sky.rain.fog.sun.data.CityTags
import java.lang.Thread.sleep

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
                    val cityList = it.getCitesListFromLocalStorage(cityTag)
                    if (cityList != null) {
                        postValue(AppState.Success(cityList))
                    } else {
                        postValue(AppState.Error(it.errorGettingCitesList()))
                    }
                }
            }
        }.start()
    }
}