package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_pricing_guide_farmhouse.*


class PricingGuideFarmHouse : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pricing_guide_farmhouse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mProceedNextFarmhouse.setOnClickListener { mProceedNextFunction() }
        mPricingGuideBackArrowFarmhouse.setOnClickListener { mPricingGuideBackArrow() }


    }

    private fun mProceedNextFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, DateAndTimeFarmHouse()).commit()
    }

    private fun mPricingGuideBackArrow() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SelectServices()).commit()
    }

}
