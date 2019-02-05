package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.adapters.BillAdapter
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForCarService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_bill.*

class Bill : BaseNavigationFragment() {

    private var adapter: BillAdapter? = null
    private var user_id: String? = null
    internal var db = FirebaseFirestore.getInstance()
    internal var notebookRef = db.collection("Users")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bill, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)


        //mBillingBackArrow.setOnClickListener { mCarServiceBackArrowFunction() }


        user_id = FirebaseAuth.getInstance().uid
        recyclerView!!.layoutManager = LinearLayoutManager(mainActivity)

        billing_progress.visibility = VISIBLE
        notebookRef.document(user_id!!).collection("Services").document("For Car Service").collection("Car Washing")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val notesList = ArrayList<ForCarService>()

                        for (document in task.result) {
                            val p = document.toObject(ForCarService::class.java)
                            p.documentId = document.id
                            notesList.add(p)
                        }
                        adapter = BillAdapter(mainActivity, notesList)
                        recyclerView!!.adapter = adapter
                    } else {
                        Toast.makeText(mainActivity, "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                    billing_progress.visibility = INVISIBLE
                }


    }

    private fun mAddMoreFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCategories()).commit()
    }

    private fun mConfirmCarFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedServices()).commit()
    }

    /*private fun mCarServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCategories()).commit()

    }*/
}
