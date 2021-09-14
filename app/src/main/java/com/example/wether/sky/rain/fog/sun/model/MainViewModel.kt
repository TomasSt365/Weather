package com.example.wether.sky.rain.fog.sun.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wether.sky.rain.fog.sun.controller.ControllerImpl
import java.lang.Thread.sleep
import com.example.wether.sky.rain.fog.sun.data.CityTags

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val controller: ControllerImpl = ControllerImpl()) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSource(cityTag: CityTags){
        getDataFromLocalSource(cityTag)
    }

    private fun getDataFromLocalSource(cityTag: CityTags) {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(controller.getWeatherFromLocalStorage(cityTag)))
        }.start()
    }
}