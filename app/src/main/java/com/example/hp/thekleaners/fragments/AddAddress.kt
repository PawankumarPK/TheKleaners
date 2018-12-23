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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
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


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        user_id = FirebaseAuth.getInstance().uid

        storageReference = FirebaseStorage.getInstance().reference

        addAddress_progress.visibility = View.VISIBLE
        mContinueAddAdress.isEnabled = false



        firebaseFirestore!!.collection("Users").document(user_id!!).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                if (task.result.exists()) {

                    val name = task.result.getString("name")
                    val surname = task.result.getString("surname")
                    val number = task.result.getString("number")

                    mAddress.setText(name)
                    mLandmark.setText(surname)
                    PinCode.setText(number)

                }

            } else {

                val error = task.exception!!.message
                Toast.makeText(context, "(FIRESTORE Retrieve Error) : $error", Toast.LENGTH_LONG).show()

            }
            addAddress_progress.visibility = View.INVISIBLE
            mContinueAddAdress.isEnabled = true
        }


        mContinueAddAdress.setOnClickListener(View.OnClickListener {
            val user_name = mAddress.text.toString()
            val surname_name = mLandmark.text.toString()
            val number_name = PinCode.text.toString()
            if (!TextUtils.isEmpty(user_name) || !TextUtils.isEmpty(surname_name) || !TextUtils.isEmpty(number_name)) {

                addAddress_progress.visibility = View.VISIBLE

                storeFirestore(null, user_name, surname_name, number_name)
            }
        })


    }


    private fun storeFirestore(task: Task<UploadTask.TaskSnapshot>?, user_name: String, surname: String, number: String) {

        val userMap = HashMap<String, String>()
        userMap["name"] = user_name
        userMap["surname"] = surname
        userMap["number"] = number

        firebaseFirestore!!.collection("Users").document(user_id!!).collection("Address").add(userMap as Map<String, Any>).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(context, "User Settings are updated", Toast.LENGTH_SHORT).show()

                fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()


            }
        }

}




private fun mContinueAddAdressFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }

    private fun mAddAddressBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }
}