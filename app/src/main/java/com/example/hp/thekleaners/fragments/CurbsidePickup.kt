package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.adapters.ViewPagerCubsidePickupService
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_curbside_pickup.*
import java.util.*

class CurbsidePickup : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_curbside_pickup, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCurbsidePickupBackArrow.setOnClickListener { mRecycleByMailServiceBackArrowFunction() }

        //mainActivity.title_name.text = resources.getString(R.string.signIn)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)


        val viewPagerAdapter = ViewPagerCubsidePickupService(mainActivity)
        curbsidePickupViewPager.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
    }

    inner class MyTimerTask : TimerTask() {

        override fun run() {

            mainActivity.runOnUiThread(java.lang.Runnable {

                if (curbsidePickupViewPager == null) {
                    return@Runnable
                }

                when {
                    curbsidePickupViewPager.currentItem == 0 -> curbsidePickupViewPager.currentItem = 1
                    curbsidePickupViewPager.currentItem == 1 -> curbsidePickupViewPager.currentItem = 2
                    else -> curbsidePickupViewPager.currentItem = 0
                }
            })
        }

    }


    private fun mRecycleByMailServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, ForHomeService()).addToBackStack(null).commit()

    }
}