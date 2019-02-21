package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForAddress
import com.example.hp.thekleaners.pojoClass.ForCarService
import com.example.hp.thekleaners.pojoClass.ForService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_saved_service_for_farms.*
import java.util.*

class SavedServiceForFarms : BaseNavigationFragment() {

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")

    var mCurrentDate: Calendar? = null
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_service_for_farms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        mSavedNewService.setOnClickListener { mSavedNewServiceFunction() }
        mSavedServiceBackArrow.setOnClickListener { mSavedServiceBackArrowFunction() }
        mCardViewCar.setOnClickListener { fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, MyInvoice()).commit() }
        //mRelativeLayoutMain.setOnClickListener {  }
        mDate.setOnClickListener { mCaDateEditextFunction() }

        savedService_progress.visibility = View.VISIBLE
        mCardView.visibility = View.GONE
        // mCardViewCar.visibility = View.GONE

        mCurrentDate = Calendar.getInstance()
        day = mCurrentDate!!.get(Calendar.DAY_OF_MONTH)
        month = mCurrentDate!!.get(Calendar.MONTH)
        year = mCurrentDate!!.get(Calendar.YEAR)


        mDate.inputType = InputType.TYPE_NULL
        month += 1
        mDate.text = "$day  / $month / $year"

        user_id = FirebaseAuth.getInstance().uid

        addressData()
        loadAddressData()
        loadCarServiceData()

        if (mServiceAmountCar.text == "Car Service")
            mCardViewCar.visibility = GONE
        else
            mCardViewCar.visibility = VISIBLE

    }

    @SuppressLint("SetTextI18n")
    private fun loadAddressData() {

        notebookRef.document(user_id!!).collection("Services").document("For Daily Picking").collection("Daily Service").get().addOnSuccessListener { queryDocumentSnapshots ->

            for (documentSnapshot in queryDocumentSnapshots) {
                val note = documentSnapshot.toObject(ForService::class.java)
                note.documentId = documentSnapshot.id

                if (mCardView == null)
                    return@addOnSuccessListener
                else {
                    mCardView.visibility = VISIBLE
                    mImageView.visibility = GONE
                }
                val documentServiceTaken = note.serviceTaken
                val documentamount = note.amount
                val documentdata = note.date


                mServiceTaken.text = documentServiceTaken

                if (day == 16) {
                    mServiceAmount.text = "300"
                    mServiceTiming.text = "$day /$month/ $year"
                } else {
                    mServiceAmount.text = "₹$documentamount"
                    mServiceTiming.text = documentdata
                }

                //mServiceAmount.text = "₹$documentamount"


            }

            savedService_progress.visibility = View.INVISIBLE

        }


    }


    @SuppressLint("SetTextI18n")
    private fun loadCarServiceData() {
        notebookRef.document(user_id!!).collection("Services").document("For Car Service").collection("Car Washing")
                .get().addOnSuccessListener { queryDocumentSnapshots ->
                    //var data = ""

                    for (documentSnapshot in queryDocumentSnapshots) {
                        val note = documentSnapshot.toObject(ForCarService::class.java)
                        note.documentId = documentSnapshot.id

                        if (mCardViewCar == null)
                            return@addOnSuccessListener
                        else {
                            mCardViewCar.visibility = VISIBLE
                            mImageView.visibility = GONE
                        }


                        val documentServiceTaken = note.carType
                        mServiceTakenCar.text = documentServiceTaken


                    }

                    savedService_progress.visibility = View.INVISIBLE


                    /*mAddressSavedAddress.text = data
                    mLandmarkSavedAddress.text = data
                    mSelectStateSavedAddress.text = data
                    mSelectCitySavedAddress.text = data
                    PinCodeSavedAddress.text = data*/
                }


    }


    private fun addressData() {
        notebookRef.document(user_id!!).collection("Address").get().addOnSuccessListener { queryDocumentSnapshots ->
            //var data = ""

            for (documentSnapshot in queryDocumentSnapshots) {
                val note = documentSnapshot.toObject(ForAddress::class.java)
                note.documentId = documentSnapshot.id

                // mSavedNewAddress.visibility = GONE
                val documentaddress = note.address

                if (mServiceAmountDemo == null)
                    return@addOnSuccessListener
                else
                    mServiceAmountDemo.text = documentaddress


            }
        }
    }


    private fun mSavedNewServiceFunction() {
        if (mServiceAmountDemo.text == "Daily")
            fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, AddAddress()).commit()
        else
            fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SelectServices()).commit()

    }


    private fun mSavedServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()
    }


    private fun mCaDateEditextFunction() {

        val datePickerDialog = DatePickerDialog(mainActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var monthOfYear = monthOfYear

            monthOfYear += 1
            mDate.text = dayOfMonth.toString() + "-" + monthOfYear + "-" + year


            //mCalculate.text = "$getAmountSum"
        }, year, month, day)
        datePickerDialog.show()
    }

    /*private fun homePricingFunction() {

        if (pref.homeAndFlat) {
            fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, PricingGuide()).commit()
        } else

            fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, PricingGuideFarmHouse()).commit()


    }
*/
//saved
}

