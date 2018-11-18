package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.Adapters.ViewPagerOtherService
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_other_service.*
import java.util.*

class OtherService : BaseNavigationFragment()  {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_other_service, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mOtherServiceBackArrow.setOnClickListener { mRecycleByMailServiceBackArrowFunction() }
        //mainActivity.title_name.text = resources.getString(R.string.signIn)


        val viewPagerAdapter = ViewPagerOtherService(mainActivity)
        otherServiceViewPager.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
    }

    inner class MyTimerTask : TimerTask() {

        override fun run() {

            mainActivity.runOnUiThread(java.lang.Runnable {

                if (otherServiceViewPager== null) {
                    return@Runnable
                }

                when {
                    otherServiceViewPager.currentItem == 0 -> otherServiceViewPager.currentItem = 1
                    otherServiceViewPager.currentItem == 1 -> otherServiceViewPager.currentItem = 2
                    else -> otherServiceViewPager.currentItem = 0
                }
            })
        }

    }

    private fun mRecycleByMailServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, ForHomeService()).addToBackStack(null).commit()
    }


}
