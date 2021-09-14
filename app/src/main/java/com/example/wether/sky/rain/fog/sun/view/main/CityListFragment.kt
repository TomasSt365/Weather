package com.example.wether.sky.rain.fog.sun.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wether.sky.rain.fog.sun.data.CityTags
import com.example.wether.sky.rain.fog.sun.data.Weather
import com.example.wether.sky.rain.fog.sun.databinding.FragmentCityListBinding
import com.example.wether.sky.rain.fog.sun.model.AppState
import com.example.wether.sky.rain.fog.sun.model.MainViewModel
import com.example.wether.sky.rain.fog.sun.view.Navigation
import com.example.wether.sky.rain.fog.sun.view.main.adapter.CityListAdapter
import com.example.wether.sky.rain.fog.sun.view.main.adapter.OnItemViewClickListener
import com.google.android.material.snackbar.Snackbar
import com.example.wether.sky.rain.fog.sun.R
import com.example.wether.sky.rain.fog.sun.R.string.*

class CityListFragment : Fragment(), View.OnClickListener, OnItemViewClickListener {
    private lateinit var viewModel: MainViewModel
    private var navigation: Navigation? = null

    private var _binding: FragmentCityListBinding? = null
    private val binding: FragmentCityListBinding
        get() = _binding!!

    /**Иконка должна соответствовать тегу!*/
    private val defaultCityTag = CityTags.World
    private val defaultCityIc = R.drawable.ic_world

    private var adapter = CityListAdapter()
    private var cityTag: CityTags? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)
        initViewAndVars()

        return binding.root
    }

    private fun initViewAndVars() {
        navigation = Navigation(requireActivity().supportFragmentManager)
        binding.mainFragmentFAB.setOnClickListener(this)

        cityTag = defaultCityTag
        binding.mainFragmentFAB.setImageResource(defaultCityIc)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        cityTag?.let { getResourcesFromLocalServer(it) }
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
        cityTag.let { viewModel.getWeatherFromLocalSource(it) }
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

                    /**Правило для cityTag и FAB иконки:
                     * 1)Сравнимое значение не должно совпадать с устанавливаемым:
                     * сравн. знач. -> {
                     *          binding.mainFragmentFAB.setImageResource(!= иконка сравн. знач.)
                     *          cityTag != сравн. знач.
                     * }
                     *
                     * 2)Устанавлемое значение должно совпадать с сравнимуемым следующим, если оно есть:
                     * сравн. знач. -> {
                     *          binding.mainFragmentFAB.setImageResource(иконка след.сравн.)
                     *          cityTag = след.сравн.
                     * } cityTag = след.сравн.
                     *
                     * 3)Если сравнение последнее, то утанавлемое значение должно совпадать с 1-м сравнимуемым:
                     * сравн. знач. -> {
                     *          binding.mainFragmentFAB.setImageResource(иконка 1-го сравн.)
                     *          cityTag = 1-е сравн.
                     * }
                     * */

                    /**Нереализованные локали не должны быть в списке !!!*/

                    cityTag = when (cityTag) {
                        CityTags.RU -> {
                            binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
                            CityTags.World
                        }
                        /*CityTags.EU -> {
                            //binding.mainFragmentFAB.setImageResource(R.drawable.)
                            CityTags.World
                        }*/
                        CityTags.World -> {
                            binding.mainFragmentFAB.setImageResource(R.drawable.ic_ru)
                           CityTags.RU
                        }
                        else -> CityTags.World
                    }
                    viewModel.getWeatherFromLocalSource(cityTag!!)
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

    companion object {
        fun newInstance(): CityListFragment {
            val args = Bundle()
            val fragment = CityListFragment()

            fragment.arguments = args
            return fragment
        }
    }
}