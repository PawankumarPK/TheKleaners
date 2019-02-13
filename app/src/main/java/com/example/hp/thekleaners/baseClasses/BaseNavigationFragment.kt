package com.example.hp.thekleaners.baseClasses



import android.os.Bundle

import android.support.v4.app.Fragment
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.utils.Preferences

abstract class BaseNavigationFragment : Fragment() {

    lateinit var mainActivity: NavigationDrawer
    lateinit var pref: Preferences
    // lateinit var homeActivity: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as NavigationDrawer
        pref = Preferences().getInstance(activity!!)
//        homeActivity = activity as MainActivity

    }

}