package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.adapters.ViewPagerRecyclingService
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_recycling_service.*
import java.util.*

class RecyclingService : BaseNavigationFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycling_service, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mRecyclingBackArrow.setOnClickListener { mRecycleByMailServiceBackArrowFunction() }

        val viewPagerAdapter = ViewPagerRecyclingService(mainActivity)
        recyclingViewPager.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
    }

    inner class MyTimerTask : TimerTask() {

        override fun run() {

            mainActivity.runOnUiThread(java.lang.Runnable {

                if (recyclingViewPager == null) {
                    return@Runnable
                }

                when {
                    recyclingViewPager.currentItem == 0 -> recyclingViewPager.currentItem = 1
                    recyclingViewPager.currentItem == 1 -> recyclingViewPager.currentItem = 2
                    else -> recyclingViewPager.currentItem = 0
                }
            })
        }

    }

    private fun mRecycleByMailServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, ForHomeService()).addToBackStack(null).commit()
    }


}
