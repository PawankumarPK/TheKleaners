package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.fragment_car_pricing_details.*

class CarPricingDetails : BaseNavigationFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_car_pricing_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = this.arguments!!.getString("doctor_id").toString()
        mOriginalPricing.text = name

        mProceedNext.setOnClickListener { mProceedNextFunction() }
        mCarPricingGuideBackArrow.setOnClickListener { mCarPricingGuideBackArrowFunction() }

    }

    private fun mProceedNextFunction() {
        val args = Bundle()
        args.putString("doctor_id", mOriginalPricing.text.toString())
        val newFragment = DateAndTimeCarService()
        newFragment.arguments = args

        fragmentManager!!.beginTransaction().replace(R.id.containerView, newFragment).commit()
    }

    private fun mCarPricingGuideBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCategories()).commit()
    }


}
