package com.example.wether.sky.rain.fog.sun.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wether.sky.rain.fog.sun.controller.ControllerImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val controller: ControllerImpl = ControllerImpl()) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSource(){
        getDataFromLocalSource()
    }

    private fun getDataFromLocalSource() {
        liveDataToObserve.postValue(AppState.Loading)
        //fixme: loadingLayout не выводиться
        Thread {
            sleep(1500)
            liveDataToObserve.postValue(AppState.Success(controller.getWeatherFromLocalStorage()))
        }.start()
    }
}