package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.BaseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_add_address.*


class AddAddress : BaseNavigationFragment() {

    private var user_id: String? = null
    private var storageReference: StorageReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
       // mContinueAddAdress.setOnClickListener { mContinueAddAdressFunction() }
        mAddAddressBackArrow.setOnClickListener { mAddAddressBackArrowFunction() }

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        user_id = FirebaseAuth.getInstance().uid
        storageReference = FirebaseStorage.getInstance().reference

         addAddress_progress.visibility = View.VISIBLE
         //mContinueAddAdress.isEnabled = false

/*
        firebaseFirestore!!.collection("Users").addSnapshotListener { documentSnapshots, e ->
            if (e != null) {
                //Log.d("", "Error : " + e.message)
            }
            for (doc in documentSnapshots.documentChanges) {
                if (doc.type == DocumentChange.Type.ADDED) {
                    // Log.d("Brand Name: ", doc.document.id)
                    doc.document.reference.collection("Address").addSnapshotListener { documentSnapshots, e ->
                        if (e != null) {
                            Log.d("", "Error : " + e.message)
                        }
                        for (doc in documentSnapshots.documentChanges) {
                            if (doc.type == DocumentChange.Type.ADDED) {
                                Log.d("SubBrands Name: ", doc.document.id)

                                doc.document.id
                                val address = doc.document.getString("Address")
                                val landmark = doc.document.getString("Landmark")
                                val pincode = doc.document.getString("Pincode")
                                val selectState = doc.document.getString("State")
                                val selectCity = doc.document.getString("City")

                              */
/*  mAddress.setText(address)
                                mLandmark.setText(landmark)
                                PinCode.setText(pincode)
                                mSelectState.setText(selectState)
                                mSelectCity.setText(selectCity)*//*

                            }
                        }
                    }

                }

            }

        }

        addAddress_progress.visibility = View.INVISIBLE
        mContinueAddAdress.isEnabled = true
*/

        mContinueAddAdress.setOnClickListener {
            val address = mAddress.text.toString()
            val landmark = mLandmark.text.toString()
            val pincode = PinCode.text.toString()
            val selectState = mSelectState.text.toString()
            val selectCity = mSelectCity.text.toString()

            if (!TextUtils.isEmpty(address) || !TextUtils.isEmpty(landmark) || !TextUtils.isEmpty(pincode) ||
                    !TextUtils.isEmpty(selectState) || !TextUtils.isEmpty(selectCity)) {

                addAddress_progress.visibility = View.INVISIBLE
                storeFirestore(null, address, landmark, pincode, selectState, selectCity)
            }
        }
    }


    private fun storeFirestore(task: Task<UploadTask.TaskSnapshot>?, address: String, landmark: String, pincode: String, state: String, city: String) {

        val userMap = HashMap<String, String>()
        userMap["Address"] = address
        userMap["Landmark"] = landmark
        userMap["Pincode"] = pincode
        userMap["State"] = state
        userMap["City"] = city

        firebaseFirestore!!.collection("Users").document(user_id!!).collection("Address").add(userMap as Map<String, Any>).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(context, "User Address are updated", Toast.LENGTH_SHORT).show()


                mContinueAddAdressFunction()

            }
        }
    }
    private fun mContinueAddAdressFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

    private fun mAddAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }
}