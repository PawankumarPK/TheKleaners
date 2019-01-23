package com.example.hp.thekleaners.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_edit_address.*
import java.util.*


class EditAddress : BaseNavigationFragment() {

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.document("")
    private var firebaseFirestore: FirebaseFirestore? = null

    var name: Boolean = true
    var farmname: Boolean = true
    private var mRef: DatabaseReference? = null
    private lateinit var database: DatabaseReference


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

    fun updateDescription() {

        val name = mEditAddress.getText().toString()
        val updatedValue = HashMap<String, String>()

        //updatedValue.put("/Address")
        //noteRef.update(KEY_DESCRIPTION, description);


        //  mRef!!.child("Address").child(user_id).child("address").setValue(name)
        //mRef!!.updateChildren(updatedValue as Map<String, Any>?)

        val result = HashMap<String, String>()
        result["address"] = "Your Description comes here"

        FirebaseDatabase.getInstance().reference.child("Users").child("Address").child("FfriNnYQCHfPvhRiWmXB").child("landmark").updateChildren((result as Map<String, Any>?)!!)
    }


    private fun writeNewPost(address: String, landmark: String, pincode: String, selectCity: String, selectState: String, house: String) {

        val user_address = mEditAddress.text.toString()

        val key = database.child("Users").push().key ?: return

        //  val userMap dn't get = HashMap<String, String>()

        val post = ForAddress(address, landmark, pincode, selectCity, selectState, house)
        val postValues = post.address

        val childUpdates = HashMap<String, Any>()
        //childUpdates["/Users/$user_id"] = postValues
        childUpdates["Users/$user_id/Address/$user_id/$address"] = user_address
        ///database.child("Users").child(user_id).child("Address").child(user_id).child("address").setValue(user_address)
        database.updateChildren(childUpdates)
    }


    private fun updateVAlue(){
        val user_address = mEditAddress.text.toString()
        mRef!!.child("ynpYyrwXxe0wNffapPBG")
                .child("address").setValue(user_address).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "User  are updated", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(context, "User  not updated", Toast.LENGTH_SHORT).show()
                    }
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


    private fun mSavedNewAddressFunction() {
        updateVAlue()

        // pref.homeAndFlat = name
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

    private fun mSavedNewAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

}
//https://firebase.google.com/docs/database/android/read-and-write#update_specific_fields