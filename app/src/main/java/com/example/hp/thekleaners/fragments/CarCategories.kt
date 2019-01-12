package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.fragment_car_categories.*


class CarCategories : BaseNavigationFragment() {

    var name: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_car_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setDefaultSetting()
        radioShifts.setOnCheckedChangeListener(radioListener)
        mDemoButton.setOnClickListener { demoFun() }
        //radioListener()
    }

    @SuppressLint("SetTextI18n")
    private val radioListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        when (group) {
            radioShifts -> changeName(checkedId)
        }
        when (checkedId) {
            R.id.radioButton1 -> {
                txtProgram.text = "200"
                radioButton1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
            }
            R.id.radioButton2 -> {
                txtProgram.text = "300"
                radioButton2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
            }
            R.id.radioButton3 -> {
                txtProgram.text = "400"
                radioButton3.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button))
            }
        }
    }

    private fun setDefaultSetting() {
        name = pref.homeAndFlat

        if (name)
            radioButton1.isChecked = true
        else
            radioButton2.isChecked = false

    }

    private fun changeName(checkedId: Int) {
        name = checkedId == R.id.radioButton1
    }

    private fun demoFun() {
        val args = Bundle()
        args.putString("doctor_id", txtProgram.text.toString())
        val newFragment = CarPricingDetails()
        newFragment.arguments = args

        fragmentManager!!.beginTransaction().replace(R.id.containerView, newFragment).commit()
    }
}



