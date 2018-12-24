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
    private var storageReference: StorageReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null


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
        mSavedNewAddress.setOnClickListener { thankuDialog() }
        // mSavedNewService.setOnClickListener { mSavedNewServiceFunction() }
        mDateBackArrow.setOnClickListener { mDateBackArrowFunction() }


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        user_id = FirebaseAuth.getInstance().uid
        storageReference = FirebaseStorage.getInstance().reference

        /*  timing_progress.visibility = View.VISIBLE
          mSavedNewAddress.isEnabled = false*/


/*

        mSavedNewAddress.setOnClickListener{
            val serviceTaken = mDailyServiceTaken.toString()
            val serviceAmount = mDailyServiceAmount.toString()
            val serviceTiming = mDailyServiceTiming.toString()
            if (!TextUtils.isEmpty(serviceTaken) || !TextUtils.isEmpty(serviceAmount) || !TextUtils.isEmpty(serviceTiming)) {

                timing_progress.visibility = View.VISIBLE

                storeFirestore(null, serviceTaken, serviceAmount, serviceTiming)
            }
        }


    }

*/


        mSavedNewAddress.setOnClickListener {
            val serviceTaken = mDailyServiceTaken.text.toString()
            val serviceAmount = mDailyServiceAmount.text.toString()
            val serviceTiming = mDailyServiceTiming.text.toString()

            if (!TextUtils.isEmpty(serviceTaken) || !TextUtils.isEmpty(serviceAmount) || !TextUtils.isEmpty(serviceTiming)) {

                timing_progress.visibility = View.VISIBLE
                storeFirestore(null, serviceTaken, serviceAmount, serviceTiming)
            }
        }
        timing_progress.visibility = View.INVISIBLE
    }


    private fun storeFirestore(task: Task<UploadTask.TaskSnapshot>?, serviceTaken: String, serviceAmount: String, serviceTiming: String) {

        val userMap = HashMap<String, String>()
        userMap["Service Taken"] = serviceTaken
        userMap["Service Amount"] = serviceAmount
        userMap["Service Start Timing"] = serviceTiming

        firebaseFirestore!!.collection("Users").document(user_id!!).collection("Services").add(userMap as Map<String, Any>).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(context, "Service settings are updated", Toast.LENGTH_SHORT).show()

                thankuDialog()
                // fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()

            }
        }
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
