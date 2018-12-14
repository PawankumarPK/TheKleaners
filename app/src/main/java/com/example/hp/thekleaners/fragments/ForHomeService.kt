package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_for_home_service.*

class ForHomeService : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_for_home_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mainActivity.tabLayout.visibility = View.GONE
        mHomeServiceBackArrow.setOnClickListener { mHomeServiceBackArrowFunction() }
        mLinearLayoutRecycleByMail.setOnClickListener { mLinearLayoutRecycleByMailFunction() }
        mLinearLayoutCubsidePickup.setOnClickListener { mLinearLayoutCubsidePickupFunction() }
        mLinearLayoutMedical.setOnClickListener { mLinearLayoutMedicalFunction() }
        mLinearLayoutRecyclingService.setOnClickListener { mLinearLayoutRecyclingServiceFunction() }
        mForHomeContinue.setOnClickListener { mForHomeContinueFunction() }
//n

    }


    private fun mHomeServiceBackArrowFunction() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)
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

    private fun mForHomeContinueFunction() {
        val intent = Intent(context, SignUpKleaners::class.java)
        startActivity(intent)

    }
}