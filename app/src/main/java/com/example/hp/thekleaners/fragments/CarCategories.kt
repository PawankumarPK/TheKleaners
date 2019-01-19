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
        mCarServiceBackArrow.setOnClickListener { mCarServiceBackArrowFunction() }
        //mDemoButton.visibility = View.GONE

    }

    @SuppressLint("SetTextI18n")
    private val radioListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->

        when (checkedId) {
            R.id.mHatchback -> {
                txtProgram.text = "HATCHBACK"
               // mHatchback.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
                demoFun()
            }
            R.id.mSedan -> {
                txtProgram.text = "SEDAN"
               // mSedan.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
                demoFun()
            }
            R.id.mLuv -> {
                txtProgram.text = "LUV"
               // mLuv.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
                demoFun()
            }
            R.id.mSuvMuv -> {
                txtProgram.text = "SUV/MUV"
               // mSuvMuv.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
                demoFun()
            }
            R.id.mPrimieryLuxury -> {
                txtProgram.text = "LUXURY"
               // mPrimieryLuxury.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
                demoFun()
            }
        }
    }

    private fun demoFun() {
        val args = Bundle()
        args.putString("doctor_id", txtProgram.text.toString())
        val newFragment = CarDetails()
        newFragment.arguments = args

        fragmentManager!!.beginTransaction().replace(R.id.containerView, newFragment).commit()
    }

    private fun mCarServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCleaning()).commit()
    }

}



