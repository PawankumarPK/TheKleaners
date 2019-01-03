package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.adapters.ViewPagerAdapter
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
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
/*        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.VISIBLE*/
        (activity as NavigationDrawer).setDrawerLocked(false)

        mContinueDailyService.setOnClickListener { mContinueDailyServiceFunction() }

        val viewPagerAdapter = ViewPagerAdapter(mainActivity)
        viewPager.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)

        mCarWashButton.setOnClickListener { mCarWashButtonServiceFunction() }
        mOthersButton.setOnClickListener { mCarWashButtonServiceFunction() }

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

    private fun mContinueDailyServiceFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, ForHomeService()).commit()
    }

    private fun mCarWashButtonServiceFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, UpcomingServices()).commit()
    }

}

