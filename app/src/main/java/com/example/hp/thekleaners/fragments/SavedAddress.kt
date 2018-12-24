package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.BaseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_saved_address.*

class SavedAddress : BaseNavigationFragment() {

    private var user_id: String? = null
    private var storageReference: StorageReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        user_id = FirebaseAuth.getInstance().uid
        storageReference = FirebaseStorage.getInstance().reference

        savedAddress_progress.visibility = View.VISIBLE
        mSavedNewAddress.isEnabled = false


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

                                    val address = doc.document.getString("Address")
                                    val landmark = doc.document.getString("Landmark")
                                    val pincode = doc.document.getString("Pincode")
                                    val selectState = doc.document.getString("State")
                                    val selectCity = doc.document.getString("City")

                                    mAddressSavedAddress.text = address
                                    mLandmarkSavedAddress.text = landmark
                                    PinCodeSavedAddress.text = pincode
                                    mSelectStateSavedAddress.text = selectState
                                    mSelectCitySavedAddress.text = selectCity
                                }
                            }
                    }
                }

            }

        }

        savedAddress_progress.visibility = View.INVISIBLE
        mSavedNewAddress.isEnabled = true

        mSavedNewAddress.setOnClickListener { mSavedNewAddressFunction() }
        mSavedNewAddressBackArrow.setOnClickListener { mSavedNewAddressBackArrowFunction() }


    }

    private fun mSavedNewAddressFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, AddAddress()).commit()
    }


    private fun mSavedNewAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()
    }
}
