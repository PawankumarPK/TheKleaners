package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        mContinueAddAdress.setOnClickListener { mContinueAddAdressFunction() }
        mAddAddressBackArrow.setOnClickListener { mAddAddressBackArrowFunction() }

    }
    private fun mContinueAddAdressFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

    private fun mAddAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }
}