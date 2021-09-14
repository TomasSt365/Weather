package com.example.wether.sky.rain.fog.sun.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wether.sky.rain.fog.sun.R
import com.example.wether.sky.rain.fog.sun.R.string.ErrorText
import com.example.wether.sky.rain.fog.sun.R.string.SuccessMessage
import com.example.wether.sky.rain.fog.sun.data.CityTags
import com.example.wether.sky.rain.fog.sun.data.Weather
import com.example.wether.sky.rain.fog.sun.databinding.FragmentCityListBinding
import com.example.wether.sky.rain.fog.sun.model.AppState
import com.example.wether.sky.rain.fog.sun.model.MainViewModel
import com.example.wether.sky.rain.fog.sun.view.Navigation
import com.example.wether.sky.rain.fog.sun.view.main.adapter.CityListAdapter
import com.example.wether.sky.rain.fog.sun.view.main.adapter.OnItemViewClickListener
import com.google.android.material.snackbar.Snackbar

class CityListFragment : Fragment(), View.OnClickListener, OnItemViewClickListener {

    private val icons: HashMap<CityTags, Int> = HashMap()//todo: переделать через EnumMap c исп. lazy

    private lateinit var viewModel: MainViewModel
    private var navigation: Navigation? = null

    private var _binding: FragmentCityListBinding? = null
    private val binding: FragmentCityListBinding
        get() = _binding!!

    private lateinit var sp: SharedPreferences

    private var adapter = CityListAdapter()
    private var cityTag: CityTags = CityTags.World

    private fun initIcons() {
        icons[CityTags.World] = R.drawable.ic_world
        icons[CityTags.RU] = R.drawable.ic_ru
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIcons()
        initViewAndVars()
        readSPSettings()
        icons[cityTag]?.let { binding.mainFragmentFAB.setImageResource(it) }
        initAdapter()
        getResourcesFromLocalServer(cityTag)
    }

    private fun initViewAndVars() {
        navigation = Navigation(requireActivity().supportFragmentManager)
        binding.mainFragmentFAB.setOnClickListener(this)
        sp = requireActivity()
            .getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)
    }

    private fun initAdapter() {
        binding.mainFragmentRecyclerView.adapter = adapter
        adapter.setOnItemViewClickListener(this)
    }

    private fun getResourcesFromLocalServer(cityTag: CityTags) {
        viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)
        viewModel
            .getLiveData()
            .observe(
                viewLifecycleOwner,
                Observer<AppState> { appState: AppState -> renderData(appState) })
        viewModel.getWeatherFromLocalSource(cityTag)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                val throwable = appState.error
                val errorText = resources.getString(ErrorText)
                Snackbar.make(binding.root, "$errorText $throwable", Snackbar.LENGTH_SHORT).show()
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                val weather = appState.weatherData
                val successMessage = resources.getString(SuccessMessage)
                adapter.setWeather(weather)
                Snackbar.make(binding.root, successMessage, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view) {
                binding.mainFragmentFAB -> {

                    /**Правило для cityTag:
                     * 1)Сравнимое значение не должно совпадать с устанавливаемым:
                     * сравн. знач. -> cityTag != сравн. знач.
                     *
                     * 2)Устанавлемое значение должно совпадать с сравнимуемым следующим, если оно есть:
                     * сравн. знач. -> cityTag = след.сравн.
                     *
                     * 3)Если сравнение последнее, то утанавлемое значение должно совпадать с 1-м сравнимуемым:
                     * сравн. знач. -> cityTag = 1-е сравн.
                     * */

                    /**Нереализованные локали не должны быть в списке !!!*/

                    cityTag = when (cityTag) {
                        CityTags.RU -> CityTags.World
                        /*CityTags.EU -> CityTags.World*/
                        CityTags.World -> CityTags.RU
                        else -> CityTags.World
                    }
                    icons[cityTag]?.let { binding.mainFragmentFAB.setImageResource(it) }
                    viewModel.getWeatherFromLocalSource(cityTag)
                    writeSP()
                }
            }
        }
    }

    override fun onItemClick(weather: Weather) {
        val bundle = Bundle()
        bundle.putParcelable(WeatherScreenFragment.WEATHER_KEY, weather)
        if (navigation != null) {
            navigation!!
                .addFragment(
                    containerId = R.id.main_fragment_container,
                    fragment = WeatherScreenFragment.newInstance(bundle),
                    addToBackStack = true)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        navigation = null
    }


    @SuppressLint("CommitPrefEdits")
    private fun writeSP() {
        val editor = sp.edit()
        editor.putString(CITY_TAG_KEY, cityTag.tag)
        Log.d("mylogs", "write: ${cityTag.tag}")
        editor.apply()
    }

    private fun readSPSettings() {
        sp.getString(CITY_TAG_KEY, CityTags.World.tag).also {
            if (it != null) {
                cityTag = CityTags.getEnumByTag(it)
                Log.d("mylogs", "it: $it")
                Log.d("mylogs", "read: $cityTag")
            }
        }
    }

    companion object {
        const val SP_KEY = "SP_KEY"
        const val CITY_TAG_KEY = "CITY_TAG_KEY"

        fun newInstance(): CityListFragment {
            val args = Bundle()
            val fragment = CityListFragment()

            fragment.arguments = args
            return fragment
        }
    }
}