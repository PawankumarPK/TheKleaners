package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForCarService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_cardetails.*
import java.text.DecimalFormat
import java.util.*


class CarDetails : BaseNavigationFragment() {

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")
    private var firebaseFirestore: FirebaseFirestore? = null

    var mCurrentDate: Calendar? = null
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cardetails, container, false)

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        firebaseFirestore = FirebaseFirestore.getInstance()
        user_id = FirebaseAuth.getInstance().uid

        mCarType.isEnabled = false
        mCarAmt.isEnabled = false
        //mCarType.inputType = InputType.TYPE_NULL

        val name = this.arguments!!.getString("doctor_id").toString()
        val carNum = this.arguments!!.getString("doctor_carAmount").toString()
      //  val carSingleNum = this.arguments!!.getDouble("doctor_carSingleAmount")
        mCarAmt?.setText(carNum)
        mCarType?.setText(name)
      //  mCarPerDayAmt.setText("$carSingleNum")

        //val sum = 30 - day
      //  val getAmountSum = sum * carSingleNum
       // roundTwoDecimals(getAmountSum)
       // mCalculate.text = "$getAmountSum"
        cardetail_progress.visibility = View.INVISIBLE


        // mDate.setOnClickListener { mDateFunction() }
        mAddmore.setOnClickListener {
            // mCalculate.text = "$getAmountSum"
            addNote()
        }
        mDoneToDate.setOnClickListener { addNoteForConfirm() }
        mCurbsidePickupBackArrow.setOnClickListener { mCurbsidePickupBackArrowFunction() }


        mCurrentDate = Calendar.getInstance()
        day = mCurrentDate!!.get(Calendar.DAY_OF_MONTH)
        month = mCurrentDate!!.get(Calendar.MONTH)
        year = mCurrentDate!!.get(Calendar.YEAR)

        mCaDateEditext.inputType = InputType.TYPE_NULL
        month += 1
        //mCaDateEditext.setText("$day + / + $month + / + $year")
        mDate.setOnClickListener { mCaDateEditextFunction() }
        mCaDateEditext.setOnClickListener { mCaDateEditextFunction() }

        demoImage.setOnClickListener { Function() }
       // demoImage.performClick()


    }

    private fun addNote() {

        when {
            mCarNameEditext.text.toString().isEmpty() -> {
                mCarNameEditext.error = "Empty"
                return
            }
            mCarNumberEditext.text.toString().isEmpty() -> {
                mCarNumberEditext.error = "Empty"
                return

            }
            mCaDateEditext.text.toString().isEmpty() -> {
                mCaDateEditext.error = "Empty"
                return
            }
            else -> {

                val carname = mCarNameEditext!!.text.toString()
                val carnumber = mCarNumberEditext!!.text.toString()
                val cartype = mCarType!!.text.toString()
                val cardate = mCaDateEditext!!.text.toString()
                val caramount = mCarAmt!!.text.toString().toInt()
                val carsingleamount = mCarPerDayAmt!!.text.toString().toDouble()
                val caramountcalc = mCalculate!!.text.toString().toDouble()

                cardetail_progress.visibility = View.VISIBLE

                val note = ForCarService(carname, carnumber, cartype, cardate, caramount, carsingleamount, caramountcalc)

                notebookRef.document(user_id!!).collection("Services").document("For Car Service").collection("Car Washing").add(note)
                Toast.makeText(context, "Detail Saved", Toast.LENGTH_SHORT).show()
                mCarNameEditext.setText("")
                mCarNumberEditext.setText("")
                fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCategories()).commit()
            }
        }
    }

    private fun addNoteForConfirm() {

        when {



            mCarNameEditext.text.toString().isEmpty() -> {
                mCarNameEditext.error = "Empty"
                return
            }
            mCarNumberEditext.text.toString().isEmpty() -> {
                mCarNumberEditext.error = "Empty"
                return
            }
            mCaDateEditext.text.toString().isEmpty() -> {
                mCaDateEditext.error = "Empty"
                return
            }
            else -> {
                val carname = mCarNameEditext!!.text.toString()
                val carnumber = mCarNumberEditext!!.text.toString()
                val cartype = mCarType!!.text.toString()
                val cardate = mCaDateEditext!!.text.toString()
                val caramount = mCarAmt!!.text.toString().toInt()
                val carsingleamount = mCarPerDayAmt!!.text.toString().toDouble()
                val caramountcalc = mCalculate!!.text.toString().toDouble()

                cardetail_progress.visibility = View.VISIBLE

                val note = ForCarService(carname, carnumber, cartype, cardate, caramount, carsingleamount, caramountcalc)

                notebookRef.document(user_id!!).collection("Services").document("For Car Service").collection("Car Washing").add(note)

                mCarNameEditext.setText("")
                mCarNumberEditext.setText("")
                //mCaDateEditext.setText("")

                fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarServiceDetails()).commit()
            }
        }
    }

    private fun mCaDateEditextFunction() {
        val carSingleNum = this.arguments!!.getDouble("doctor_carSingleAmount")
        mCarPerDayAmt.setText("$carSingleNum")
        val datePickerDialog = DatePickerDialog(mainActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var monthOfYear = monthOfYear

            monthOfYear += 1
            mCaDateEditext.setText(dayOfMonth.toString() + "-" + monthOfYear + "-" + year)
            if (dayOfMonth == 31){
                Toast.makeText(context, "Choose Another Day", Toast.LENGTH_LONG).show()
                mCaDateEditext.setText("")
                mCalculate.text = ""
                return@OnDateSetListener
            }
            val sum = 30 - dayOfMonth
            val getAmountSum = sum * carSingleNum
            mCalculate.text = String.format("%.2f", getAmountSum)
            //mCalculate.text = "$getAmountSum"
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun mCurbsidePickupBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCategories()).commit()
    }

    private fun Function() {
        Toast.makeText(context, "DEMO", Toast.LENGTH_LONG).show()
    }
}
