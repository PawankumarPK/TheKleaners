package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.hp.thekleaners.BaseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.android.synthetic.main.dialog_thanku.*
import kotlinx.android.synthetic.main.fragment_saved_address.*
import javax.xml.transform.Source

class SavedAddress : BaseNavigationFragment() {

    private val displayRectangle = Rect()
    private var width = 0
    private lateinit var dialog: Dialog
    private lateinit var metrics: DisplayMetrics


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


        metrics = DisplayMetrics()
        mainActivity.window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        width = (displayRectangle.width() * 0.9f).toInt()
        dialog = Dialog(mainActivity)


        // firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        //  user_id = FirebaseAuth.getInstance().uid
        storageReference = FirebaseStorage.getInstance().reference

        mSavedNewAddress.setOnClickListener { mSavedNewAddressFunction() }
        mSavedNewAddressBackArrow.setOnClickListener { mSavedNewAddressBackArrowFunction() }
        thankuDialog()

        //  savedAddress_progress.visibility = View.VISIBLE
        //  mSavedNewAddress.isEnabled = false


    }


    private fun mDialogContinueFunction() {

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

                            val addressSaved = doc.document.getString("Address")
                            val landmarkSaved = doc.document.getString("Landmark")
                            val pincodeSaved = doc.document.getString("Pincode")
                            val selectStateSaved = doc.document.getString("State")
                            val selectCitySaved = doc.document.getString("City")

                            if (!documentSnapshots.isEmpty) {
                                mAddressSavedAddress.text = addressSaved
                                mLandmarkSavedAddress.text = landmarkSaved
                                PinCodeSavedAddress.text = pincodeSaved
                                mSelectStateSavedAddress.text = selectStateSaved
                                mSelectCitySavedAddress.text = selectCitySaved
                            }
                        }


                    }

                }

            }
        }

    }

        @SuppressLint("InflateParams")
        private fun thankuDialog() {
            val layout = LayoutInflater.from(mainActivity).inflate(R.layout.dialog_logout, null)
            layout.minimumWidth = width
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 20, 0, 0)
            dialog.setContentView(layout)
            dialog.mCancelDialog.setOnClickListener { dialog.dismiss()  }
            dialog.buttonLogout.setOnClickListener { mDialogContinueFunction() }
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()

        }

    private fun mSavedNewAddressFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, AddAddress()).commit()
    }


    private fun mSavedNewAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()
    }

    override fun onStart() {
        super.onStart()

        mAddressSavedAddress.text = null
        mLandmarkSavedAddress.text = null
        PinCodeSavedAddress.text = null
        mSelectStateSavedAddress.text = null
        mSelectCitySavedAddress.text = null
    }
}
