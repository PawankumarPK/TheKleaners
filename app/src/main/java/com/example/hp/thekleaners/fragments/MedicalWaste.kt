package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.Adapters.ViewPagerMedical
import com.example.hp.thekleaners.BaseClasses.ForHomeServiceBaseFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.ForHomeService
import kotlinx.android.synthetic.main.fragment_medical.*
import java.util.*

class MedicalWaste : ForHomeServiceBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_medical, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMedicalBackArrow.setOnClickListener { mRecycleByMailServiceBackArrowFunction() }
        val viewPagerAdapter = ViewPagerMedical(homeServiceActivity)
        medicalWasteViewPager.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(), 2000, 4000)
    }

    inner class MyTimerTask : TimerTask() {

        override fun run() {

            homeServiceActivity.runOnUiThread(java.lang.Runnable {

                if (medicalWasteViewPager == null) {
                    return@Runnable
                }

                when {
                    medicalWasteViewPager.currentItem == 0 -> medicalWasteViewPager.currentItem = 1
                    medicalWasteViewPager.currentItem == 1 -> medicalWasteViewPager.currentItem = 2
                    else -> medicalWasteViewPager.currentItem = 0
                }
            })
        }

    }

    private fun mRecycleByMailServiceBackArrowFunction() {
        val intent = Intent(context, ForHomeService::class.java)
        startActivity(intent)
    }
}
