package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.BaseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_saved_new_services.*

class SavedServices: BaseNavigationFragment() {

    private var user_id: String? = null
    private var storageReference: StorageReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved_new_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mSavedNewService.setOnClickListener { mSavedNewServiceFunction() }
        mSavedServiceBackArrow.setOnClickListener { mSavedServiceBackArrowFunction() }



        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        user_id = FirebaseAuth.getInstance().uid
        storageReference = FirebaseStorage.getInstance().reference

        savedService_progress.visibility = View.VISIBLE
        mSavedNewService.isEnabled = false


        /* firebaseFirestore!!.collection("Users").addSnapshotListener { documentSnapshots, e ->
            if (e != null) {
                //Log.d("", "Error : " + e.message)
            }
            for (doc in documentSnapshots.documentChanges) {
                if (doc.type == DocumentChange.Type.ADDED) {
                    // Log.d("Brand Name: ", doc.document.id)
                    doc.document.reference.collection("Services").addSnapshotListener { documentSnapshots, e ->
                        if (e != null) {
                            Log.d("", "Error : " + e.message)
                        }
                        for (doc in documentSnapshots.documentChanges) {
                            if (doc.type == DocumentChange.Type.ADDED) {

                                val address = doc.document.getString("Service Taken")
                                val landmark = doc.document.getString("Service Amount")
                                val pincode = doc.document.getString("Service Start Timing")


                                mServiceTaken.text = address
                                mServiceAmount.text = landmark
                                mServiceTiming.text = pincode

                            }
                            else {

                                Toast.makeText(context, "(FIRESTORE Retrieve Error)", Toast.LENGTH_LONG).show()

                            }
                        }

                    }
                }

            }
*/

       /* firebaseFirestore!!.collection("Users").document(user_id!!).collection("Services").addSnapshotListener { documentSnapshots, e ->
            if (e != null) {
                Log.d("", "Error : " + e.message)
            }
            if (task.isSuccessful) {

                if (task.result.exists()) {

                    val name = task.result.getString("name")
                    val surname = task.result.getString("surname")
                    val number = task.result.getString("number")

                    mServiceTaken.text = address
                    mServiceAmount.text = landmark
                    mServiceTiming.text = pincode

                }

            } else {

                val error = task.exception!!.message
                Toast.makeText(context, "(FIRESTORE Retrieve Error) : $error", Toast.LENGTH_LONG).show()

            }
            savedService_progress.visibility = View.INVISIBLE
            //setup_btn.isEnabled = true
        }
    */

        savedService_progress.visibility = View.INVISIBLE
        mSavedNewService.isEnabled = true


    }

    private fun mSavedNewServiceFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SelectServices()).commit()
    }


    private fun mSavedServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()
    }

}
