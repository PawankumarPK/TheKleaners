package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.adapters.ViewPagerRecycleByMailService
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_recyclable_by_mail.*
import java.util.*

class RecyclableByMail : BaseNavigationFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclable_by_mail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        mRecycleByMailServiceBackArrow.setOnClickListener { mRecycleByMailServiceBackArrowFunction() }
        val viewPagerAdapter = ViewPagerRecycleByMailService(mainActivity)
        recycleByMailServiceViewPager.adapter = viewPagerAdapter
        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
    }

    inner class MyTimerTask : TimerTask() {

        override fun run() {

            mainActivity.runOnUiThread(java.lang.Runnable {

                if (recycleByMailServiceViewPager == null) {
                    return@Runnable
                }

                when {
                    recycleByMailServiceViewPager.currentItem == 0 -> recycleByMailServiceViewPager.currentItem = 1
                    recycleByMailServiceViewPager.currentItem == 1 -> recycleByMailServiceViewPager.currentItem = 2
                    else -> recycleByMailServiceViewPager.currentItem = 0
                }
            })
        }

    }

    private fun mRecycleByMailServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, ForHomeService()).addToBackStack(null).commit()
    }


}