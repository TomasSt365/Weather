package com.example.wether.sky.rain.fog.sun.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.example.wether.sky.rain.fog.sun.R
import com.example.wether.sky.rain.fog.sun.view.main.CityListFragment
import com.example.wether.sky.rain.fog.sun.controller.NetworkBroadcastReceiver

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    private val navigation = Navigation(supportFragmentManager)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolBar()
        val intentFilter = IntentFilter()
        val receiver = NetworkBroadcastReceiver()
        intentFilter.addAction(ConnectivityManager.EXTRA_NETWORK)
        registerReceiver(receiver, intentFilter)

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