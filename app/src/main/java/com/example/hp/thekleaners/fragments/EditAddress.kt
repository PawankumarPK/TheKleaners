package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForAddress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_edit_address.*

import com.google.firebase.database.*


class EditAddress : BaseNavigationFragment() {

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.document("")
    private var firebaseFirestore: FirebaseFirestore? = null

    var name: Boolean = true
    var farmname: Boolean = true
    private var mRef: DatabaseReference? = null
    private lateinit var database: DatabaseReference

     val updateData = FirebaseDatabase.getInstance().getReference("Users").child(user_id!!).child("Address")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        setDefaultSetting()
        mRadioGroupName.setOnCheckedChangeListener(radioListener)
        firebaseFirestore = FirebaseFirestore.getInstance()

        database = FirebaseDatabase.getInstance().reference

        mRef = FirebaseDatabase.getInstance().reference.child("Users")

        mEditAddress_progress.visibility = VISIBLE

        mContinuEditAdress.setOnClickListener { mSavedNewAddressFunction() }
        mEditAddressBackArrow.setOnClickListener { mSavedNewAddressBackArrowFunction() }


        user_id = FirebaseAuth.getInstance().uid

        loadAddressData()
        // radioFunction()

    }


    private fun loadAddressData() {
        notebookRef.collection("Users").document(user_id!!).collection("Address").get().addOnSuccessListener { queryDocumentSnapshots ->
            //var data = ""

            var dataaddress = ""
            var datalandmark = ""
            var datapincode = ""
            var datastate = ""
            var datacity = ""


            for (documentSnapshot in queryDocumentSnapshots) {
                val note = documentSnapshot.toObject(ForAddress::class.java)
                note.documentId = documentSnapshot.id


                val documentaddress = note.address
                val documentlandmark = note.landmark
                val documentpincode = note.pincode
                val documentstate = note.selectState
                val documentcity = note.selectCity

                dataaddress += documentaddress
                datalandmark += documentlandmark
                datapincode += documentpincode
                datastate += documentstate
                datacity += documentcity

                // data += "\n\n"
            }
            mEditAddress.setText(dataaddress)
            mEditLandmark.setText(datalandmark)
            mEditSelectState.setText(datastate)
            mEditSelectCity.setText(datacity)
            mEditPinCode.setText(datapincode)

            mEditAddress_progress.visibility = View.INVISIBLE

        }
    }


    private val radioListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        when (group) {
            mRadioGroupName -> changeName(checkedId)

        }
    }


    private fun setDefaultSetting() {
        name = pref.homeAndFlat
        //farmname = pref.farmHouse

        if (name) {
            mAutomaticGenerate.isChecked = true
        } else {
            mAlwaysAsk.isChecked = true
        }
    }


    private fun changeName(checkedId: Int) {
        name = checkedId == R.id.mAutomaticGenerate
    }


    private fun updateArtist(documentId: String, address: String, landmark: String,
                             pincode: String,selectState : String,selectCity: String): Boolean {
        //getting the specified artist reference
        val dR = FirebaseDatabase.getInstance().getReference("Users").child(user_id!!).child("Address").child(documentId)

        //updating artist
        val artist = ForAddress(documentId,address,landmark,pincode,selectState,selectCity)
        dR.setValue(artist)
        Toast.makeText(mainActivity, "Artist Updated", Toast.LENGTH_LONG).show()
        return true
    }

    private fun mSavedNewAddressFunction() {

        updateData.child("address").setValue("Pawan YAdav");

        val address = mEditAddress!!.text.toString()
        val landmark = mEditLandmark!!.text.toString()
        val pincode = mEditPinCode!!.text.toString()
        val selectState = mEditSelectState!!.text.toString()
        val selectCity = mEditSelectCity!!.text.toString()
        val radioButton = textview_selected!!.text.toString()
        //addAddress_progress.visibility = View.VISIBLE

        /*val dR = FirebaseDatabase.getInstance().getReference("Users").child(user_id!!).child("Address").child("r4anBLVT358navSiQk13")
        val note = ForAddress(address, landmark, pincode, selectState, selectCity, radioButton)
        dR.setValue(note)*/
        Toast.makeText(mainActivity, "Artist Updated", Toast.LENGTH_LONG).show()
        //updateArtist().document(user_id!!).collection("Address").add(note)
        updateArtist(radioButton,address,landmark,pincode,selectState,selectCity)

        // pref.homeAndFlat = name
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

    private fun mSavedNewAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

}
//https://firebase.google.com/docs/database/android/read-and-write#update_specific_fields