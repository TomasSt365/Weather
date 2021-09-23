package com.example.wether.sky.rain.fog.sun.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wether.sky.rain.fog.sun.R.string.ErrorLoadingWeather
import com.example.wether.sky.rain.fog.sun.controller.WeatherDTO
import com.example.wether.sky.rain.fog.sun.controller.WeatherLoaderListener
import com.example.wether.sky.rain.fog.sun.data.Weather
import com.example.wether.sky.rain.fog.sun.databinding.FragmentWetherScreenBinding
import com.example.wether.sky.rain.fog.sun.view.Navigation
import com.google.android.material.snackbar.Snackbar

class WeatherScreenFragment : Fragment(), WeatherLoaderListener {
    private var navigation: Navigation? = null

    private var _binding: FragmentWetherScreenBinding? = null
    private val binding: FragmentWetherScreenBinding
        get() = _binding!!
    private var currentWeather: Weather? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWetherScreenBinding.inflate(inflater, container, false)
        navigation = Navigation(requireActivity().supportFragmentManager)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            currentWeather = (requireArguments().get(WEATHER_KEY) as Weather?)!!
        }
        currentWeather?.let { setData(it) }
    }

    private fun setData(weather: Weather) {
        with(binding) {
            cityName.apply {
                text = weather.city.name
            }
            temperatureValue.apply {
                text = weather.temperature.toString()
            }
            feelsLikeValue.apply {
                text = weather.feelsLike.toString()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        navigation = null
    }

    companion object {
        const val WEATHER_KEY = "WEATHER_KEY"
        fun newInstance(bundle: Bundle): WeatherScreenFragment {
            val fragment = WeatherScreenFragment()

            with(fragment) {
                arguments = bundle
                return this
            }
        }
    }

    override fun onLoaded(weatherDTO: WeatherDTO) {
        TODO("Not yet implemented")
    }

    override fun onFailed(error: Throwable) {
        val errorMessage = resources.getString(ErrorLoadingWeather)
        with(binding){
            errorScreen.visibility = View.VISIBLE
            errorMsg.apply { 
                text = error.message
            }
        }
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE).show()
    }
}