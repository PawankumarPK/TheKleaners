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
    private var mDatabase: FirebaseDatabase? = null
    //private var mRef: DatabaseReference? = null
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

        mDatabase = FirebaseDatabase.getInstance()
        database = FirebaseDatabase.getInstance().reference



        mEditAddress_progress.visibility = VISIBLE

        mContinuEditAdress.setOnClickListener { mSavedNewAddressFunction() }
        mEditAddressBackArrow.setOnClickListener { mSavedNewAddressBackArrowFunction() }


        user_id = FirebaseAuth.getInstance().uid

        loadAddressData()

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


            val mAddAddress  = mEditAddress.setText(dataaddress)
            val mAddLandmark = mEditLandmark.setText(datalandmark)
            val mAddSelectState= mEditSelectState.setText(datastate)
            val mAddSelectCity= mEditSelectCity.setText(datacity)
            val mAddPinCode= mEditPinCode.setText(datapincode)


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

        FirebaseDatabase.getInstance().reference.child("Users").child("Address").child("FfriNnYQCHfPvhRiWmXB").child("landmark").updateChildren(result as Map<String, Any>?)
    }
/*

    private fun writeNewPost(userId: String, username: String, title: String, body: String) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        val key = database.child("Users").push().key
        if (key == null) {

            return
        }

        val post = ForAddress(mAddress, mEditLandmark, mEditSelectState, mEditSelectCity,mEditPinCode)
        val postValues = post.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/posts/$key"] = postValues
        childUpdates["/user-posts/$userId/$key"] = postValues

        database.updateChildren(childUpdates)
    }
*/

/*

    private fun writeNewUser() {

        val name = mEditAddress.text.toString()

         //   val user = ForAddress(address, landmark, pincode, selectState, selectCity)
        database.child("Users").child(user_id).child("Address").child("FfriNnYQCHfPvhRiWmXB").child("address").setValue(name)
    }
*/


    private fun mSavedNewAddressFunction() {

        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }


    private fun mSavedNewAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

}
//https://firebase.google.com/docs/database/android/read-and-write#update_specific_fields