package com.example.wether.sky.rain.fog.sun.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.wether.sky.rain.fog.sun.R
import com.example.wether.sky.rain.fog.sun.view.main.CityListFragment

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    private val navigation = Navigation(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolBar()

        navigation.replaceFragment(
            containerId = R.id.main_fragment_container,
            fragment = CityListFragment.newInstance(),
            addToBackStack = false
        )
    }

    /**TollBarWork*/
    private fun initToolBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)
    }

    /**BackButtonWork*/
    override fun onBackStackChanged() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}