package com.example.wether.sky.rain.fog.sun.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wether.sky.rain.fog.sun.R.string.*
import com.example.wether.sky.rain.fog.sun.data.WeatherDTO
import com.example.wether.sky.rain.fog.sun.controller.WeatherLoader
import com.example.wether.sky.rain.fog.sun.controller.WeatherLoaderListener
import com.example.wether.sky.rain.fog.sun.databinding.FragmentCityDetailsBinding
import com.example.wether.sky.rain.fog.sun.view.Navigation
import com.google.android.material.snackbar.Snackbar
import com.example.wether.sky.rain.fog.sun.data.City
import com.example.wether.sky.rain.fog.sun.data.*

class DetailsCityScreenFragment : Fragment(), WeatherLoaderListener {
    private var navigation: Navigation? = null

    private var _binding: FragmentCityDetailsBinding? = null
    private val binding: FragmentCityDetailsBinding
        get() = _binding!!

    private val currentCity: City by lazy {
        (arguments?.getParcelable(CITY_KEY)) ?: getDefaultCity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCityDetailsBinding.inflate(inflater, container, false)
        navigation = Navigation(requireActivity().supportFragmentManager)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WeatherLoader(this, currentCity.lat, currentCity.lon).loadWeather()
    }

    private fun showWeather(weatherDTO: WeatherDTO) {
        with(binding) {
            cityName.apply {
                text = currentCity.name
            }
            temperatureValue.apply {
                text = "${weatherDTO.fact.temp}"
            }
            feelsLikeValue.apply {
                text = "${weatherDTO.fact.feels_like}"
            }
            weatherCondition.apply {
                text = "${weatherDTO.fact.condition}"
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

    override fun onLoaded(weatherDTO: WeatherDTO) {
        showWeather(weatherDTO)
    }

    override fun onFailed(errors: List<String>) {
        with(binding) {
            errorScreen.visibility = View.VISIBLE
            errorMsg.apply {
                var errorList = ""
                errors.forEach {
                    errorList += "$it\n"
                }
                text = errorList
            }
        }
        Snackbar.make(binding.root, ErrorText, Snackbar.LENGTH_INDEFINITE)
            .setAction(BackActionText) {
                requireActivity().onBackPressed()
            }
            .show()
    }

    companion object {
        const val CITY_KEY = "CITY_KEY"
        fun newInstance(bundle: Bundle): DetailsCityScreenFragment {
            val fragment = DetailsCityScreenFragment()

            with(fragment) {
                arguments = bundle
                return this
            }
        }
    }

}