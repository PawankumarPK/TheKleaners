package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.fragment_monthly_invoice.*

class MonthlyInvoice : BaseNavigationFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_monthly_invoice, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRelativeLayoutOne.setOnClickListener { mRelativeLayoutOneFunction() }
        mRelativeLayoutTwo.setOnClickListener { mRelativeLayoutTwoFunction() }
        mRelativeLayoutThree.setOnClickListener { mRelativeLayoutThreeFunction() }
        mRelativeLayoutFour.setOnClickListener { mRelativeLayoutFourFunction() }
    }


    private fun mRelativeLayoutOneFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, VisitWebsite()).commit()
    }

    private fun mRelativeLayoutTwoFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, AboutUs()).commit()
    }

    private fun mRelativeLayoutThreeFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, HelpCenter()).commit()
    }

    private fun mRelativeLayoutFourFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, JuneBill()).commit()
    }
}