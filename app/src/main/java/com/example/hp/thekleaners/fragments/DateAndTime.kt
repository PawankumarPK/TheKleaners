package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.hp.thekleaners.BaseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.pojoClass.ForService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.dialog_thanku.*
import kotlinx.android.synthetic.main.fragment_date_and_time.*


class DateAndTime : BaseNavigationFragment() {

    private val displayRectangle = Rect()
    private var width = 0
    private lateinit var dialog: Dialog
    private lateinit var metrics: DisplayMetrics


    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")


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
        dialog = Dialog(mainActivity)
        mSavedNewService.setOnClickListener { addNote() }
        // mSavedNewService.setOnClickListener { mSavedNewServiceFunction() }
        mDateBackArrow.setOnClickListener { mDateBackArrowFunction() }

        user_id = FirebaseAuth.getInstance().uid

    }

    private fun addNote() {
        val serviceTaken = mDailyServiceTaken!!.text.toString()
        val amount = mDailyServiceAmount!!.text.toString()
        val timing = mDailyServiceTiming!!.text.toString()

        val note = ForService(serviceTaken, amount, timing)

        notebookRef.document(user_id!!).collection("Services").add(note)

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

    private fun mDateBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, PricingGuide()).commit()
        dialog.dismiss()
    }

}
