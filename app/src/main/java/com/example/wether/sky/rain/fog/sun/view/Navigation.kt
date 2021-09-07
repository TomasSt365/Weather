package com.example.wether.sky.rain.fog.sun.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Navigation(
    private val fragmentManager: FragmentManager
) {

    fun addFragment(
        containerId: Int,
        fragment: Fragment?,
        addToBackStack: Boolean
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (addToBackStack) {
            fragmentTransaction.addToBackStack("")
        }
        fragmentTransaction
            .replace(containerId, fragment!!)
            .commit()
    }

    fun addFragment(
        containerId: Int,
        fragment: Fragment?,
        addToBackStack: Boolean,
        backStackTag: String?
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStackTag)
        }
        fragmentTransaction
            .replace(containerId, fragment!!)
            .commit()
    }

    fun addFragment(
        containerId: Int,
        fragment: Fragment?,
        addToBackStack: Boolean,
        popBackStackBeforeAdd: Boolean
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (popBackStackBeforeAdd) {
            fragmentManager.popBackStack()
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack("")
        }
        fragmentTransaction
            .replace(containerId, fragment!!)
            .commit()
    }

    fun addFragment(
        containerId: Int,
        fragment: Fragment?,
        addToBackStack: Boolean,
        popBackStackBeforeAdd: Boolean,
        backStackTag: String?
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (popBackStackBeforeAdd) {
            fragmentManager.popBackStack()
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStackTag)
        }
        fragmentTransaction
            .replace(containerId, fragment!!)
            .commit()
    }
}