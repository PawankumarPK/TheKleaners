package com.example.hp.thekleaners.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_car_cleaning.*

class CarCleaning : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_car_cleaning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mCarServiceBackArrow.setOnClickListener { mCarServiceBackArrowFunction() }
        mForCarCleaningContinue.setOnClickListener { mForCarCleaningContinueFunction() }


    }
    private fun mCarServiceBackArrowFunction() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)
    }
    private fun mForCarCleaningContinueFunction() {
        //mRelativeLayoutForGone.visibility = View.GONE
        fragmentManager!!.beginTransaction().replace(R.id.containerView, CarCategories()).addToBackStack(null).commit()

    }


}
