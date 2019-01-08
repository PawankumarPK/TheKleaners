package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForAddress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_add_address.*
import kotlinx.android.synthetic.main.fragment_edit_address.*


class AddAddress : BaseNavigationFragment() {


    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")
    // private lateinit var radioButton: RadioButton


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mContinueAddAdress.setOnClickListener { addNote() }
        mAddAddressBackArrow.setOnClickListener { mAddAddressBackArrowFunction() }

        user_id = FirebaseAuth.getInstance().uid
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.mHomeAndFlatAddAddress -> textview.text = "Home Or Flats"
                R.id.mFarmHouseAddAddress  -> textview.text = "Farm House"

            }
        }

        //mContinueAddAdress.isEnabled = false

    }


    private fun addNote() {
        val address = mAddress!!.text.toString()
        val landmark = mLandmark!!.text.toString()
        val pincode = PinCode!!.text.toString()
        val selectState = mSelectState!!.text.toString()
        val selectCity = mSelectCity!!.text.toString()
        val radioButton = textview!!.text.toString()
        addAddress_progress.visibility = View.VISIBLE


        val note = ForAddress(address, landmark, pincode, selectState, selectCity,radioButton)

        notebookRef.document(user_id!!).collection("Address").add(note)
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

    private fun radioFunction() {


    }

    private fun mContinueAddAdressFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

    private fun mAddAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }
}