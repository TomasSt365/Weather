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
import androidx.lifecycle.ViewModelProvider
import com.example.wether.sky.rain.fog.sun.R
import com.example.wether.sky.rain.fog.sun.R.drawable.*
import com.example.wether.sky.rain.fog.sun.R.string.*
import com.example.wether.sky.rain.fog.sun.data.City
import com.example.wether.sky.rain.fog.sun.data.CityTags
import com.example.wether.sky.rain.fog.sun.data.CityTags.*
import com.example.wether.sky.rain.fog.sun.databinding.FragmentCityListBinding
import com.example.wether.sky.rain.fog.sun.model.AppState
import com.example.wether.sky.rain.fog.sun.model.MainViewModel
import com.example.wether.sky.rain.fog.sun.view.Navigation
import com.example.wether.sky.rain.fog.sun.view.main.adapter.CityListAdapter
import com.example.wether.sky.rain.fog.sun.view.main.adapter.OnItemViewClickListener
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.HashMap

class CityListFragment : Fragment(), View.OnClickListener, OnItemViewClickListener {

    private val icons: HashMap<CityTags, Int> = HashMap()

    private lateinit var viewModel: MainViewModel
    private var navigation: Navigation? = null

    private var _binding: FragmentCityListBinding? = null
    private val binding: FragmentCityListBinding
        get() = _binding!!

    private lateinit var sp: SharedPreferences

    private var adapter = CityListAdapter()
    private var cityTag: CityTags = World
    private var startResponseTime: Double = 0.0

    private fun initIcons() {
        icons[World] = ic_world
        icons[RU] = ic_ru
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
        initAdapter()
        initViewModel(cityTag)
    }

    private fun initViewAndVars() {
        navigation = Navigation(requireActivity().supportFragmentManager)
        binding.mainFragmentFAB.setOnClickListener(this)
        sp = requireActivity()
            .getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)
    }

    private fun initAdapter() {
        with(adapter) {
            binding.mainFragmentRecyclerView.adapter = this
            setOnItemViewClickListener(this@CityListFragment)
        }
    }

    private fun initViewModel(cityTag: CityTags) {
        viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        viewModel
            .getLiveData()
            .observe(
                viewLifecycleOwner, { appState: AppState -> renderData(appState) })

        getWeatherFromServer(cityTag)
    }

    private fun getWeatherFromServer(cityTag: CityTags) {
        icons[cityTag]?.let { binding.mainFragmentFAB.setImageResource(it) }

        startResponseTime = viewModel.getWeatherFromLocalSource(cityTag).run {
            System.currentTimeMillis()
        }.toDouble().also {
            Log.d("mylogs", "startResponseTime: $it")
        }
    }

    private fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    errorScreen.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                    val throwableMsg = appState.error.message
                    val errorText = resources.getString(ErrorText)
                    val snackbar = Snackbar
                        .make(root, "$throwableMsg", Snackbar.LENGTH_INDEFINITE)
                    errorMsg.apply {
                        text = errorText
                    }
                    snackbar
                        .setAction(TryAgainText) {
                            snackbar.tryAgain()
                        }.show()
                }
                AppState.Loading -> {
                    mainFragmentFAB.visibility = View.GONE
                    loadingLayout.visibility = View.VISIBLE
                }
                is AppState.Success -> {
                    mainFragmentFAB.visibility = View.VISIBLE
                    errorScreen.visibility = View.GONE
                    loadingLayout.visibility = View.GONE
                    val cites = appState.citesData
                    val successMessage = resources.getString(SuccessMessage)
                    val responseTimeMsg = resources.getString(ResponseTimeMsg)
                    adapter.setWeather(cites)

                    val responseTime = (System.currentTimeMillis() - startResponseTime) / 1000

                    Snackbar
                        .make(root,
                            "$successMessage\n$responseTimeMsg ${
                                responseTime.also {
                                    Log.d("mylogs",
                                        "responseTime: $it")
                                }
                            } s",
                            Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onClick(view: View?) {
        //todo: сделать так чтобы при длином нажатии на FAB выскакивало окно с выбором локации,
        // по возможности сделать открытие этого окна с анимацией
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
                        RU -> World
                        /*EU -> World*/
                        World -> RU
                        else -> World
                    }
                    getWeatherFromServer(cityTag)
                    writeSP()
                }
            }
        }
    }

    override fun onItemClick(city: City) {
        val bundle = Bundle()
        with(bundle) {
            putParcelable(DetailsCityScreenFragment.CITY_KEY, city)
            if (navigation != null) {
                navigation!!
                    .addFragment(
                        containerId = R.id.main_fragment_container,
                        fragment = DetailsCityScreenFragment.newInstance(this),
                        addToBackStack = true)
            }
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
        with(editor) {
            putString(CITY_TAG_KEY, cityTag.tag)
            Log.d("mylogs", "write: ${cityTag.tag}")
            apply()
        }
    }

    private fun readSPSettings() {
        sp.getString(CITY_TAG_KEY, World.tag).also {
            if (it != null) {
                cityTag = CityTags.getEnumByTag(it).also { tag ->
                    Log.d("mylogs", "read: $tag")
                }
            }
        }
    }

    private fun Snackbar.tryAgain() {
        binding.errorScreen.visibility = View.GONE
        getWeatherFromServer(cityTag)
        this.dismiss()
    }

    companion object {
        const val SP_KEY = "SP_KEY"
        const val CITY_TAG_KEY = "CITY_TAG_KEY"

        fun newInstance(): CityListFragment {
            val args = Bundle()
            val fragment = CityListFragment()
            with(fragment) {
                arguments = args
                return this
            }
        }
    }
}