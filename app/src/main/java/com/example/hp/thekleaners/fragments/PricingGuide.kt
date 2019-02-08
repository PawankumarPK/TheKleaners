package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.text.InputType
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
import java.text.DecimalFormat
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
        mProceedNext.setOnClickListener { addNote()  }
        mDateButton.setOnClickListener { mCaDateEditextFunction() }
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

        /*tv.setInputType(InputType.TYPE_NULL)
        month = month + 1
        tv.setText(day.toString() + "/" + month + "/" + year)
*/


        mProceedNext.visibility = View.INVISIBLE

    }

    /*private fun mProceedNextFunction() {
        addNote()
    }*/

    private fun mPricingGuideBackArrow() {

        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SelectServices()).commit()
    }

    private fun mCaDateEditextFunction() {
        //  val carSingleNum = this.arguments!!.getDouble("doctor_carSingleAmount")
        val datePickerDialog = DatePickerDialog(mainActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var monthOfYear = monthOfYear

            monthOfYear += 1
            //mDailyServiceTiming.text = dayOfMonth.toString() + "/" + monthOfYear + "/" + year
            mDailyServiceTiming.text = dayOfMonth.toString() + "/" + monthOfYear + "/" + year
           // mDailyServiceTiming.text = dayOfMonth.toString()
            if (dayOfMonth == 31) {
                Toast.makeText(context, "Choose Another Day", Toast.LENGTH_LONG).show()
                return@OnDateSetListener
            }

            val sum = 30 - dayOfMonth
            val getAmountSum = sum * 2.34
            roundTwoDecimals(getAmountSum)
            mDailyServiceAmount.text = getAmountSum.toString()
           // mDailyServiceTiming.text = day.toString() + "/" + month + "/" + year
            //mDailyServiceTiming.text = dayOfMonth.toString() + "/" + monthOfYear + "/" + year
            mProceedNext.visibility = View.VISIBLE


        }, year, month, day)
        datePickerDialog.show()
    }

    fun roundTwoDecimals(d: Double): Double {
        val twoDForm = DecimalFormat("#.##")
        return java.lang.Double.valueOf(twoDForm.format(d))
    }

    private fun addNote() {

        val serviceTaken = mDailyServiceTaken!!.text.toString()
        val amount = mDailyServiceAmount!!.text.toString()
        val timing = mDailyServiceTiming!!.text.toString()

        val note = ForService(serviceTaken, amount, timing)
        notebookRef.document(user_id!!).collection("Services").document("For Daily Picking").collection("Daily Service").add(note)
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
