package com.example.wether.sky.rain.fog.sun.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wether.sky.rain.fog.sun.R
import com.example.wether.sky.rain.fog.sun.view.main.QueryScreenFragment

class MainActivity : AppCompatActivity() {
    private val navigation = Navigation(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.addFragment(
            containerId = R.id.main_fragment_container,
            fragment = QueryScreenFragment.newInstance(),
            addToBackStack = false
        )
        //todo: запускать в дальнейшем главную страницу
    }
}