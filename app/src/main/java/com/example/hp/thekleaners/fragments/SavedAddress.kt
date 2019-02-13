package com.example.hp.thekleaners.fragments

import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForAddress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_saved_address.*


class SavedAddress : BaseNavigationFragment() {

    private val displayRectangle = Rect()
    private var width = 0
    private lateinit var dialog: Dialog
    private lateinit var metrics: DisplayMetrics

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")

    var ref = FirebaseDatabase.getInstance().reference
//    var dbNode = FirebaseDatabase.getInstance().reference.root.child("Users").child(user_id!!).child("Address")


    // notebookRef.document(user_id!!).collection("Address").get().addOnSuccessListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        mLinearLayout.visibility = INVISIBLE
        mView.visibility = INVISIBLE
        savedAddress_progress.visibility = VISIBLE

        metrics = DisplayMetrics()
        mainActivity.window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        width = (displayRectangle.width() * 0.9f).toInt()
        dialog = Dialog(mainActivity)
        mSavedNewAddress.setOnClickListener { mSavedNewAddressFunction() }
        mSavedNewAddressBackArrow.setOnClickListener { mSavedNewAddressBackArrowFunction() }
        addNewService.setOnClickListener { addNewServiceFunction() }
        mEditProfile.setOnClickListener { mEditProfileFunction() }
        user_id = FirebaseAuth.getInstance().uid

        loadAddressData()

    }


    private fun loadAddressData() {
        notebookRef.document(user_id!!).collection("Address").get().addOnSuccessListener { queryDocumentSnapshots ->
            //var data = ""

            for (documentSnapshot in queryDocumentSnapshots) {
                val note = documentSnapshot.toObject(ForAddress::class.java)
                note.documentId = documentSnapshot.id

                if (mLinearLayout == null)
                    return@addOnSuccessListener
                else {
                    mLinearLayout.visibility = VISIBLE
                    mView.visibility = VISIBLE
                    mImageView.visibility = GONE
                    addNewService.visibility = VISIBLE
                    mSavedNewAddress.visibility = GONE
                }

                val documentaddress = note.address
                val documentlandmark = note.landmark
                val documentpincode = note.pincode
                val documentstate = note.selectState
                val documentcity = note.selectCity
                val houseSelction = note.house

                mAddressSavedAddress.text = documentaddress
                mLandmarkSavedAddress.text = documentlandmark
                mSelectStateSavedAddress.text = documentstate
                mSelectCitySavedAddress.text = documentcity
                PinCodeSavedAddress.text = documentpincode
                mHomeSavedAddress.text = houseSelction

                // data += "\n\n"
            }
            savedAddress_progress.visibility = View.INVISIBLE
        }
    }


    private fun mSavedNewAddressFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, AddAddress()).commit()
    }


    private fun mSavedNewAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()
    }

    private fun addNewServiceFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SelectServices()).commit()
    }

    private fun mEditProfileFunction() {
        //dbNode.setValue(null)
        //fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, EditAddress()).commit()
          delete()
    }

    private fun delete() {
        val applesQuery = ref.child(user_id!!).child("Address")


        applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (appleSnapshot in dataSnapshot.children) {
                    //applesQuery.child(id).removeValue();
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(mainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
