package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForCarService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_cardetails.*

class CarDetails : BaseNavigationFragment() {

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")
    private var firebaseFirestore: FirebaseFirestore? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cardetails, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        firebaseFirestore = FirebaseFirestore.getInstance()
        user_id = FirebaseAuth.getInstance().uid

        val name = this.arguments!!.getString("doctor_id").toString()
        val carNum = this.arguments!!.getString("doctor_carAmount").toString()
        mCarType.text = name
        mCarAmt.text = carNum

        cardetail_progress.visibility = View.INVISIBLE

        mAddmore.setOnClickListener { addNote() }
        mDoneToDate.setOnClickListener { addNoteForConfirm() }

    }

    private fun addNote() {

        when {
            mCarNameEditext.text.toString().isEmpty() -> {
                mCarNameEditext.error = "Empty"
                return
            }
            mCarNumberEditext.text.toString().isEmpty() -> {
                mCarNumberEditext.error = "Empty"
                return
            }
            else -> {

                val carname = mCarNameEditext!!.text.toString()
                val carnumber = mCarNumberEditext!!.text.toString()
                val cartype = mCarType!!.text.toString()
                val caramount = mCarAmt!!.text.toString().toInt()

                cardetail_progress.visibility = View.VISIBLE

                val note = ForCarService(carname, carnumber, cartype, caramount)

                notebookRef.document(user_id!!).collection("Services").document("For Car Service").collection("Car Washing").add(note)
                Toast.makeText(context, "Detail Saved", Toast.LENGTH_SHORT).show()
                mCarNameEditext.setText("")
                mCarNumberEditext.setText("")
                fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCategories()).commit()
            }
        }
    }

    private fun addNoteForConfirm() {

        when {
            mCarNameEditext.text.toString().isEmpty() -> {
                mCarNameEditext.error = "Empty"
                return
            }
            mCarNumberEditext.text.toString().isEmpty() -> {
                mCarNumberEditext.error = "Empty"
                return
            }
            else -> {
                val carname = mCarNameEditext!!.text.toString()
                val carnumber = mCarNumberEditext!!.text.toString()
                val cartype = mCarType!!.text.toString()
                val caramount = mCarAmt!!.text.toString().toInt()

                cardetail_progress.visibility = View.VISIBLE

                val note = ForCarService(carname, carnumber, cartype, caramount)

                notebookRef.document(user_id!!).collection("Services").document("For Car Service").collection("Car Washing").add(note)

                mCarNameEditext.setText("")
                mCarNumberEditext.setText("")

                fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarServiceDetails()).commit()
            }
        }
    }

}
