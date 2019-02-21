package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_edit_address.*
import java.util.*


class EditAddress : BaseNavigationFragment() {

    private var user_id: String? = null
    //private val db = FirebaseFirestore.getInstance()
    private var firebaseFirestore: FirebaseFirestore? = null

    var name: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        //setDefaultSetting()
        firebaseFirestore = FirebaseFirestore.getInstance()

        mEditAddress_progress.visibility = VISIBLE

        mContinuEditAdress.setOnClickListener { addAddress() }
        mEditAddressBackArrow.setOnClickListener { mSavedNewAddressBackArrowFunction() }
        user_id = FirebaseAuth.getInstance().uid


        firebaseFirestore!!.collection("Users").document(user_id!!).collection("Address").document("$id").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                if (task.result!!.exists()) {

                    val address = task.result!!.getString("address")
                    val landmark = task.result!!.getString("landmark")
                    val pincode = task.result!!.getString("pincode")
                    val state = task.result!!.getString("state")
                    val city = task.result!!.getString("city")
                    val type = task.result!!.getString("type")


                    when {
                        mEditAddress == null -> return@addOnCompleteListener
                        mEditLandmark == null -> return@addOnCompleteListener
                        mEditPinCode == null -> return@addOnCompleteListener
                        mEditSelectState == null -> return@addOnCompleteListener
                        mEditSelectCity == null -> return@addOnCompleteListener
                        else -> {
                            mEditAddress.setText(address)
                            mEditLandmark.setText(landmark)
                            mEditPinCode.setText(pincode)
                            mEditSelectState.setText(state)
                            mEditSelectCity.setText(city)
                            mType.setText(type)
                        }

                    }
                    mEditAddress_progress.visibility = INVISIBLE


                }

            } else {

                val error = task.exception!!.message
                Toast.makeText(context, "(FIRESTORE Retrieve Error) : $error", Toast.LENGTH_LONG).show()

            }
            /* setup_progress.visibility = View.INVISIBLE
             setup_btn.isEnabled = true*/
        }


        //  loadAddressData()
        // radioFunction()

    }


    private fun addAddress() {

        mEditAddress_progress.visibility = View.VISIBLE

        val address = mEditAddress.text.toString()
        val landmark = mEditLandmark.text.toString()
        val pincode = mEditPinCode.text.toString()
        val state = mEditSelectState.text.toString()
        val city = mEditSelectCity.text.toString()
        val type = mType.text.toString()
        storeFirestore(null, address, landmark, pincode, state, city, type)
    }

    private fun storeFirestore(task: Task<UploadTask.TaskSnapshot>?, address: String, landmark: String, pincode: String, state: String, city: String, type: String) {

        val userMap = HashMap<String, String>()
        userMap["address"] = address
        userMap["landmark"] = landmark
        userMap["pincode"] = pincode
        userMap["state"] = state
        userMap["city"] = city
        userMap["type"] = type

        firebaseFirestore!!.collection("Users").document(user_id!!).collection("Address").document("$id").set(userMap as Map<String, Any>).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Update Address", Toast.LENGTH_SHORT).show()

                fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()


            }
        }
    }


    private fun mSavedNewAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

}

/*private fun loadAddressData() {
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

*/

//https://firebase.google.com/docs/database/android/read-and-write#update_specific_fields