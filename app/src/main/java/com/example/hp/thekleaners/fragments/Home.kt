package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.Adapters.ViewPagerAdapter
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class Home : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer

        val viewPagerAdapter = ViewPagerAdapter(mainActivity)
        viewPager.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
    }

    inner class MyTimerTask : TimerTask() {

        override fun run() {

            mainActivity.runOnUiThread(java.lang.Runnable {
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(1)
                } else if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(2)
                } else {
                    viewPager.setCurrentItem(0)
                }
            })
        }
    }
}
