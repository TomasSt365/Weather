package com.example.wether.sky.rain.fog.sun.controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager.EXTRA_NO_CONNECTIVITY
import android.util.Log

class NetworkBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("mylogs","NetworkBroadcastReceiver: start onReceive")
        val extras = intent?.extras
        if (extras != null) {
            val noConnectivity = intent.getBooleanExtra(EXTRA_NO_CONNECTIVITY, false)
            if(noConnectivity){
                Log.d("mylogs","Device haven't Internet connection")
            }else{
                Log.d("mylogs","Device have Internet connection")
            }
        }
    }

}