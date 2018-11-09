package com.example.hp.thekleaners.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.hp.thekleaners.activities.NavigationDrawer

abstract class BaseNavigationFragment : Fragment() {

    lateinit var mainActivity: NavigationDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as NavigationDrawer
    }
}