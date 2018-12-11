package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.dialog_thanku.*
import kotlinx.android.synthetic.main.fragment_date_and_time.*
import android.widget.LinearLayout



class DateAndTime : BaseNavigationFragment() {

    private val displayRectangle = Rect()
    private var width = 0
    private lateinit var dialog: Dialog
    private lateinit var metrics: DisplayMetrics

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_date_and_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        metrics = DisplayMetrics()
        mainActivity.window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        width = (displayRectangle.width() * 0.9f).toInt()
        //mainActivity.windowManager.defaultDisplay.getMetrics(metrics)
        dialog = Dialog(mainActivity)
        mSavedNewAddress.setOnClickListener { thankuDialog() }
        // mSavedNewService.setOnClickListener { mSavedNewServiceFunction() }
        mDateBackArrow.setOnClickListener { mDateBackArrowFunction() }

    }

    @SuppressLint("InflateParams")
    private fun thankuDialog() {
        val layout = LayoutInflater.from(mainActivity).inflate(R.layout.dialog_thanku,null)
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

    private fun mDateBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, PricingGuide()).commit()
        dialog.dismiss()
    }

}
