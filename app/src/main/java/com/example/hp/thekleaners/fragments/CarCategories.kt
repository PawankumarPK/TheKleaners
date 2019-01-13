package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_car_categories.*


class CarCategories : BaseNavigationFragment() {

    var name: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_car_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        radioShifts.setOnCheckedChangeListener(radioListener)
        mDemoButton.setOnClickListener { demoFun() }
        mCarServiceBackArrow.setOnClickListener { mCarServiceBackArrowFunction() }
        mDemoButton.visibility = View.GONE

    }

    @SuppressLint("SetTextI18n")
    private val radioListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->

        when (checkedId) {
            R.id.radioButton1 -> {
                mDemoButton.visibility = View.VISIBLE
                txtProgram.text = "200"
                radioButton1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
            }
            R.id.radioButton2 -> {
                mDemoButton.visibility = View.VISIBLE
                txtProgram.text = "300"
                radioButton2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
            }
            R.id.radioButton3 -> {
                mDemoButton.visibility = View.VISIBLE
                txtProgram.text = "400"
                radioButton3.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
            }
        }
    }

    private fun demoFun() {
        val args = Bundle()
        args.putString("doctor_id", txtProgram.text.toString())
        val newFragment = CarPricingDetails()
        newFragment.arguments = args

        fragmentManager!!.beginTransaction().replace(R.id.containerView, newFragment).commit()
    }

    private fun mCarServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCleaning()).commit()
    }

}



