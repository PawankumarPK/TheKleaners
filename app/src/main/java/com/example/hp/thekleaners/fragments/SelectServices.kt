package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_select_service.*

class SelectServices : BaseNavigationFragment() {

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        // mainActivity.title_name.text = resources.getString(R.string.signIn)
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mForDailyService.setOnClickListener { mForDailyServiceFunction() }
        mSelectServiceBackArrow.setOnClickListener { mSelectServiceBackArrowFunction() }
        mForCarCleaning.setOnClickListener { mForCarCleaningFunction() }
        mOtherService.setOnClickListener { Toast.makeText(context, "Upcoming Service", Toast.LENGTH_SHORT).show() }
        mForMedicalService.setOnClickListener { Toast.makeText(context, "Upcoming Service", Toast.LENGTH_SHORT).show() }
        mForParties.setOnClickListener { Toast.makeText(context, "Upcoming Service", Toast.LENGTH_SHORT).show() }
        mRelativeLayout.setOnClickListener { Toast.makeText(context, "Upcoming Service", Toast.LENGTH_SHORT).show() }

        selectService_progress.visibility = VISIBLE
        user_id = FirebaseAuth.getInstance().uid
        loadAddressData()
    }

    @SuppressLint("SetTextI18n")
    private fun loadAddressData() {
        notebookRef.document(user_id!!).collection("Services").document("For Daily Picking").collection("Daily Service").get().addOnSuccessListener { queryDocumentSnapshots ->

            for (documentSnapshot in queryDocumentSnapshots) {
                val note = documentSnapshot.toObject(ForService::class.java)
                note.documentId = documentSnapshot.id

                val documentServiceTaken = note.serviceTaken

                if (mDemoText == null)
                    return@addOnSuccessListener
                else
                mDemoText.text = documentServiceTaken

            }

            selectService_progress.visibility = INVISIBLE
        }
    }


    private fun mForDailyServiceFunction() {

        if (mDemoText.text == "Daily Service")
            Toast.makeText(context, "Already taken this service", Toast.LENGTH_SHORT).show()
        else
            fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, PricingGuide()).commit()
    }

    private fun mForCarCleaningFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCategories()).commit()
    }

    private fun mSelectServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedServices()).commit()
    }

}
