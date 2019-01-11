package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.fragment_car_categories.*


class Car_categories : BaseNavigationFragment() {

    var name: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_car_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // radioListener()
        setDefaultSetting()
        radioShifts.setOnCheckedChangeListener(radioListener)
        mDemoButton.setOnClickListener {  }
        //radioListener()
/*

        when (view.id) {
            R.id.radioButton1 -> str = "button1Text"
            R.id.radioButton2 -> str = "button2Text"
            R.id.radioButton3 -> str = "button3Text"
        }
*/

    }

   /* private fun radioListener() {

        when {
            radioButton1.isChecked -> {
                txtProgram.text = "one"
                Toast.makeText(context, "Working", Toast.LENGTH_SHORT).show()
            }
            radioButton2.isChecked -> {
                txtProgram.text = "Two"
                Toast.makeText(context, "Working2", Toast.LENGTH_SHORT).show()
            }
            radioButton3.isChecked -> {
                txtProgram.text = "Three"
                Toast.makeText(context, "Working3", Toast.LENGTH_SHORT).show()
            }
        }
    }
*/
    private val radioListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        when (group) {
            radioShifts -> changeName(checkedId)
        }
        when (checkedId) {
            R.id.radioButton1 -> txtProgram.text = "200"
            R.id.radioButton2 -> txtProgram.text = "300"
            R.id.radioButton3 -> txtProgram.text = "400"
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


    /*  val args = Bundle()
      args.putString("doctor_id", str)
      val newFragment = Car_Pricing_details()
      newFragment.arguments = args*/
}



