package com.example.wether.sky.rain.fog.sun.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wether.sky.rain.fog.sun.databinding.FragmentQueryScreenBinding
import com.example.wether.sky.rain.fog.sun.model.AppState
import com.example.wether.sky.rain.fog.sun.model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.example.wether.sky.rain.fog.sun.data.Weather
import com.example.wether.sky.rain.fog.sun.data.getCites

class QueryScreenFragment : Fragment() {
    private var _binding: FragmentQueryScreenBinding? = null
    private val binding: FragmentQueryScreenBinding
        get() {
            return _binding!!
        }
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQueryScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel
            .getLiveData()
            .observe(
                viewLifecycleOwner,
                Observer<AppState> { appState: AppState -> renderData(appState) })
        mainViewModel.getWeatherFromLocalSource()
        setData(getCites()[0])//todo: костыль
        //fixme: данные не выводяться
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                val throwable = appState.error
                Snackbar.make(binding.root, "ERROR $throwable", Snackbar.LENGTH_LONG).show()
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            //fixme: loadingLayout не выводиться
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setData(weather: Weather) {
        binding.cityName.text = weather.city!!.name
        binding.temperatureValue.text = weather.temperature.toString()
        binding.feelsLikeValue.text = weather.feelsLike.toString()
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    companion object {
        fun newInstance(): QueryScreenFragment {
            val args = Bundle()
            val fragment = QueryScreenFragment()
            fragment.arguments = args
            return fragment
        }

    }
}