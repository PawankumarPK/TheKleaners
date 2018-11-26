package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.Adapters.ViewPagerAdapter
import com.example.hp.thekleaners.Adapters.ViewPagerCarWash
import com.example.hp.thekleaners.Adapters.ViewPagerOtherService
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class Home : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE

        val viewPagerAdapter = ViewPagerAdapter(mainActivity)
        viewPager.adapter = viewPagerAdapter


        val carWashViewPagerAdapter = ViewPagerCarWash(mainActivity)
        mCarWash_viewPager.adapter = carWashViewPagerAdapter


        val othersViewPagerAdapter = ViewPagerOtherService(mainActivity)
        mOthers_viewPager.adapter = othersViewPagerAdapter


        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)

        val cartimer = Timer()
        cartimer.scheduleAtFixedRate(MyCarTimerTask(), 2000, 4000)


        val othertimer = Timer()
        othertimer.scheduleAtFixedRate(MyOtherTimerTask(), 2000, 4000)
    }

    inner class MyTimerTask : TimerTask() {

        override fun run() {

            mainActivity.runOnUiThread(java.lang.Runnable {

                if (viewPager == null) {
                    return@Runnable
                }

                when {
                    viewPager.currentItem == 0 -> viewPager.currentItem = 1
                    viewPager.currentItem == 1 -> viewPager.currentItem = 2
                    else -> viewPager.currentItem = 0
                }
            })
        }


    }

    inner class MyCarTimerTask : TimerTask() {

        override fun run() {

            mainActivity.runOnUiThread(java.lang.Runnable {

                if (mCarWash_viewPager == null) {
                    return@Runnable
                }

                when {
                    mCarWash_viewPager.currentItem == 0 -> mCarWash_viewPager.currentItem = 1
                    mCarWash_viewPager.currentItem == 1 -> mCarWash_viewPager.currentItem = 2
                    else -> mCarWash_viewPager.currentItem = 0
                }
            })
        }
    }
    inner class MyOtherTimerTask : TimerTask() {

        override fun run() {

            mainActivity.runOnUiThread(java.lang.Runnable {

                if (mOthers_viewPager == null) {
                    return@Runnable
                }

                when {
                    mOthers_viewPager.currentItem == 0 -> mOthers_viewPager.currentItem = 1
                    mOthers_viewPager.currentItem == 1 -> mOthers_viewPager.currentItem = 2
                    else -> mOthers_viewPager.currentItem = 0
                }
            })
        }
    }

}
