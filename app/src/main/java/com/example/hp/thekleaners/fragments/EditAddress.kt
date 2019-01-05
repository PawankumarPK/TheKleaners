package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForAddress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_edit_address.*
import java.util.*


class EditAddress : BaseNavigationFragment() {

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")
    private val KEY_DESCRIPTION = "address"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)


        mEditAddress_progress.visibility = VISIBLE

        mContinuEditAdress.setOnClickListener { mSavedNewAddressFunction() }
        mEditAddressBackArrow.setOnClickListener { mSavedNewAddressBackArrowFunction() }


        user_id = FirebaseAuth.getInstance().uid

        loadAddressData()

    }


    private fun loadAddressData() {
        notebookRef.document(user_id!!).collection("Address").get().addOnSuccessListener { queryDocumentSnapshots ->
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
            /*mAddressSavedAddress.text = data
            mLandmarkSavedAddress.text = data
            mSelectStateSavedAddress.text = data
            mSelectCitySavedAddress.text = data
            PinCodeSavedAddress.text = data*/
        }


    }


    fun updateDescription() {
        val address = mEditAddress.text.toString()

        val note = HashMap<String, Any>()
        note[KEY_DESCRIPTION] = address

        notebookRef.document(note.toString())
        //notebookRef.update(KEY_DESCRIPTION, description);
    }


    private fun addNote() {
        val address = mEditAddress!!.text.toString()
        val landmark = mEditLandmark!!.text.toString()
        val pincode = mEditPinCode!!.text.toString()
        val selectState = mEditSelectState!!.text.toString()
        val selectCity = mEditSelectCity!!.text.toString()
        mEditAddress_progress.visibility = View.VISIBLE


        val note = ForAddress(address, landmark, pincode, selectState, selectCity)

        notebookRef.document(user_id!!).collection("Address").add(note)


        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }


    private fun mSavedNewAddressFunction() {
        updateDescription()
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }


    private fun mSavedNewAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

}
