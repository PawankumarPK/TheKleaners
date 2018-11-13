package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.Adapters.ViewPagerHomeService
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_for_home_service.*
import java.util.*

class ForHomeService : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_for_home_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mHomeServiceBackArrow.setOnClickListener { mHomeServiceBackArrowFunction() }
        mLinearLayoutRecycleByMail.setOnClickListener { mLinearLayoutRecycleByMailFunction() }
        mLinearLayoutCubsidePickup.setOnClickListener { mLinearLayoutCubsidePickupFunction() }
        mLinearLayoutMedical.setOnClickListener { mLinearLayoutMedicalFunction() }
        mLinearLayoutRecyclingService.setOnClickListener { mLinearLayoutRecyclingServiceFunction() }
        mLinearLayoutOtherServices.setOnClickListener { mLinearLayoutOtherServicesFunction() }


        val viewPagerAdapter = ViewPagerHomeService(mainActivity)
        homeServiceViewPager.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
    }

    inner class MyTimerTask : TimerTask() {

        override fun run() {

            mainActivity.runOnUiThread(java.lang.Runnable {

                if (homeServiceViewPager == null) {
                    return@Runnable
                }

                when {
                    homeServiceViewPager.currentItem == 0 -> homeServiceViewPager.currentItem = 1
                    homeServiceViewPager.currentItem == 1 -> homeServiceViewPager.currentItem = 2
                    else -> homeServiceViewPager.currentItem = 0
                }
            })
        }

    }

    private fun mHomeServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, Home()).addToBackStack(null).commit()
    }

    private fun mLinearLayoutRecycleByMailFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, RecyclableByMail()).addToBackStack(null).commit()
    }

    private fun mLinearLayoutCubsidePickupFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, CurbsidePickup()).addToBackStack(null).commit()

    }

    private fun mLinearLayoutMedicalFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, MedicalWaste()).addToBackStack(null).commit()

    }

    private fun mLinearLayoutRecyclingServiceFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, RecyclingService()).addToBackStack(null).commit()

    }

    private fun mLinearLayoutOtherServicesFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, OtherService()).addToBackStack(null).commit()

    }
}