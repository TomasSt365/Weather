package com.example.wether.sky.rain.fog.sun.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wether.sky.rain.fog.sun.databinding.FragmentCityListBinding
import com.example.wether.sky.rain.fog.sun.model.AppState
import com.example.wether.sky.rain.fog.sun.model.MainViewModel
import com.example.wether.sky.rain.fog.sun.view.MainActivity
import com.example.wether.sky.rain.fog.sun.view.Navigation
import com.example.wether.sky.rain.fog.sun.R
import com.google.android.material.snackbar.Snackbar

class CityListFragment: Fragment() {
    private var _binding: FragmentCityListBinding? = null
    private val binding: FragmentCityListBinding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private var navigation : Navigation? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)
        navigation = Navigation(requireActivity().supportFragmentManager)
        //initList()
        return binding.root
    }

    private fun initList() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getResourcesFromLocalServer()
    }

    private fun getResourcesFromLocalServer() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel
            .getLiveData()
            .observe(
                viewLifecycleOwner,
                Observer<AppState> { appState: AppState -> renderData(appState) })
        //не уверен что так напрямую можно обращаться к активити
        mainViewModel.getWeatherFromLocalSource()
        if (navigation!=null){
            navigation!!.addFragment(
                containerId = R.id.main_fragment_container,
                fragment = QueryScreenFragment.newInstance(),
                addToBackStack = false)
        }//todo:временно(для проверки версии)
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
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        navigation = null
    }

    companion object{
        fun newInstance(): CityListFragment {
            val args = Bundle()
            val fragment = CityListFragment()

            fragment.arguments = args
            return fragment
        }
    }
}