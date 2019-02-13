package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.dialog_thanku.*
import kotlinx.android.synthetic.main.fragment_pricing_guide.*
import java.util.*


class PricingGuide : BaseNavigationFragment() {

    var mCurrentDate: Calendar? = null
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0

    private val displayRectangle = Rect()
    private var width = 0
    private lateinit var dialog: Dialog
    private lateinit var metrics: DisplayMetrics

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pricing_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mProceedNext.setOnClickListener { addNote() }
        mDailyServiceTiming.setOnClickListener {

            mCaDateEditextFunction()
        }
        mPricingGuideBackArrow.setOnClickListener { mPricingGuideBackArrow() }

        metrics = DisplayMetrics()
        mainActivity.window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        width = (displayRectangle.width() * 0.9f).toInt()
        dialog = Dialog(mainActivity)
        user_id = FirebaseAuth.getInstance().uid

        mCurrentDate = Calendar.getInstance()

        day = mCurrentDate!!.get(Calendar.DAY_OF_MONTH)
        month = mCurrentDate!!.get(Calendar.MONTH)
        year = mCurrentDate!!.get(Calendar.YEAR)

        mProceedNext.isEnabled = false

    }

    private fun mPricingGuideBackArrow() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SelectServices()).commit()
    }

    private fun mCaDateEditextFunction() {
        mDailyServiceTiming.setBackgroundColor(Color.WHITE)
        val datePickerDialog = DatePickerDialog(mainActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var monthOfYear = monthOfYear

            monthOfYear += 1
            mDailyServiceTiming.text = dayOfMonth.toString() + "/" + monthOfYear + "/" + year
            if (dayOfMonth == 31) {
                Toast.makeText(context, "Choose Another Day", Toast.LENGTH_LONG).show()
                mDailyServiceTiming.text = "Select Date"
                return@OnDateSetListener
            }

            val sum = 30 - dayOfMonth
            val getAmountSum = sum * 2.34
            mDailyServiceAmount.text = String.format("%.2f", getAmountSum)
            mDailyServiceTiming.setTextColor(Color.BLACK)
            mProceedNext.setBackgroundColor(Color.parseColor("#5FAB34"))
            mProceedNext.isEnabled = true

        }, year, month, day)

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel)) { dialog, which ->
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                mDailyServiceTiming.setBackgroundColor(Color.WHITE)
                mDailyServiceTiming.setTextColor(Color.BLACK)
            }
        }
        datePickerDialog.show()
    }

    private fun addNote() {

        val serviceTaken = mDailyServiceTaken!!.text.toString()
        val amount = mDailyServiceAmount!!.text.toString()
        val timing = mDailyServiceTiming!!.text.toString()

        val note = ForService(serviceTaken, amount, timing)
        notebookRef.document(user_id!!).collection("Services").document("For Daily Picking").collection("Daily Service").add(note)

        if (mDailyServiceTiming.text == "Select Date")
            Toast.makeText(context, "Select Valid Date", Toast.LENGTH_LONG).show()
        else
            thankuDialog()
    }

    @SuppressLint("InflateParams")
    private fun thankuDialog() {
        val layout = LayoutInflater.from(mainActivity).inflate(R.layout.dialog_thanku, null)
        layout.minimumWidth = width
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0, 20, 0, 0)
        dialog.setContentView(layout)
        dialog.mContinueDialog.setOnClickListener { mDialogContinueFunction() }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun mDialogContinueFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedServices()).commit()
        dialog.dismiss()
    }

}
