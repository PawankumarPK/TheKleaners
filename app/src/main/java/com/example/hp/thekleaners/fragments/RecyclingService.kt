package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.Adapters.ViewPagerRecyclingService
import com.example.hp.thekleaners.BaseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.BaseClasses.ForHomeServiceBaseFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.ForHomeService
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_recycling_service.*
import java.util.*

class RecyclingService : ForHomeServiceBaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycling_service, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclingBackArrow.setOnClickListener { mRecycleByMailServiceBackArrowFunction() }

        val viewPagerAdapter = ViewPagerRecyclingService(homeServiceActivity)
        recyclingViewPager.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
    }

    inner class MyTimerTask : TimerTask() {

        override fun run() {

            homeServiceActivity.runOnUiThread(java.lang.Runnable {

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
        val intent = Intent(context, ForHomeService::class.java)
        startActivity(intent)
    }


}
