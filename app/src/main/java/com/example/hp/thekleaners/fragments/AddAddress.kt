package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForAddress
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_add_address.*
import java.util.HashMap


class AddAddress : BaseNavigationFragment() {


    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")
    var name: Boolean = true
    // private lateinit var radioButton: RadioButton
    private var firebaseFirestore: FirebaseFirestore? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        firebaseFirestore = FirebaseFirestore.getInstance()

        setDefaultSetting()
        radioGroup.setOnCheckedChangeListener(radioListener)

        mContinueAddAdress.setOnClickListener {addAddress() }
        mAddAddressBackArrow.setOnClickListener { mAddAddressBackArrowFunction() }

        user_id = FirebaseAuth.getInstance().uid

    }

    private fun addAddress(){

        addAddress_progress.visibility = View.VISIBLE

        val address = mAddress.text.toString()
        val landmark = mLandmark.text.toString()
        val pincode = PinCode.text.toString()
        val state = mSelectState.text.toString()
        val city = mSelectCity.text.toString()
        val radioButton = textview!!.text.toString()
        storeFirestore(null, address, landmark, pincode, state, city,radioButton)
    }


    private fun storeFirestore(task: Task<UploadTask.TaskSnapshot>?, address: String, landmark: String, pincode: String, state: String, city: String,type: String) {

        ///note.documentId = documentSnapshot.id

        val userMap = HashMap<String, String>()
        userMap["address"] = address
        userMap["landmark"] = landmark
        userMap["pincode"] = pincode
        userMap["state"] = state
        userMap["city"] = city
        userMap["type"] = type


        firebaseFirestore!!.collection("Users").document(user_id!!).collection("Address").document("$id").set(userMap as Map<String, Any>).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //Toast.makeText(context, "Add Address", Toast.LENGTH_SHORT).show()

                fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()


            }
        }
        pref.homeAndFlat = name
    }



    private fun addNote() {
        val address = mAddress!!.text.toString()
        val landmark = mLandmark!!.text.toString()
        val pincode = PinCode!!.text.toString()
        val selectState = mSelectState!!.text.toString()
        val selectCity = mSelectCity!!.text.toString()
        val radioButton = textview!!.text.toString()
        addAddress_progress.visibility = View.VISIBLE


        val note = ForAddress(address, landmark, pincode, selectState, selectCity, radioButton)

        notebookRef.document(user_id!!).collection("Address").add(note)
        pref.homeAndFlat = name
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

    private val radioListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        when (group) {
            radioGroup -> changeName(checkedId)
        }
        when (checkedId) {
            R.id.mHomeAndFlatAddAddress -> textview.text = "Home Or Flats"
            R.id.mFarmHouseAddAddress -> textview.text = "Farm House"
        }
    }


    private fun setDefaultSetting() {
        name = pref.homeAndFlat

        if (name)
            mHomeAndFlatAddAddress.isChecked = true
        else
            mFarmHouseAddAddress.isChecked = false


    }

    private fun changeName(checkedId: Int) {
        name = checkedId == R.id.mHomeAndFlatAddAddress
    }

    private fun mAddAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }
}