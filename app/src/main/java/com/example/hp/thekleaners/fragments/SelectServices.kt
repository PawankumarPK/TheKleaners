package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_select_service.*

class SelectServices : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        // mainActivity.title_name.text = resources.getString(R.string.signIn)
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mForDailyService.setOnClickListener { mForDailyServiceFunction() }
        mSelectServiceBackArrow.setOnClickListener { mSelectServiceBackArrowFunction() }
        mForCarCleaning.setOnClickListener { mForCarCleaningFunction() }

    }

    private fun mForDailyServiceFunction() {
        if (pref.homeAndFlat)
            fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, PricingGuide()).commit()
        else
            fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, PricingGuideFarmHouse()).commit()
    }

    private fun mForCarCleaningFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCleaning()).commit()
    }

    private fun mSelectServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedServices()).commit()
    }

}
