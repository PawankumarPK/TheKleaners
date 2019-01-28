package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.adapters.CustomAdapter
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForCarService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.dialog_thanku_car.*
import kotlinx.android.synthetic.main.fragment_carservicedetail.*
import java.util.*


class CarServiceDetails : BaseNavigationFragment() {


    private val displayRectangle = Rect()
    private var width = 0
    private lateinit var dialog: Dialog
    private lateinit var metrics: DisplayMetrics

    private var adapter: CustomAdapter? = null
    private var user_id: String? = null
    internal var db = FirebaseFirestore.getInstance()
    internal var notebookRef = db.collection("Users")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_carservicedetail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        mCarServiceBackArrow.setOnClickListener { mCarServiceBackArrowFunction() }


        metrics = DisplayMetrics()
        mainActivity.window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        width = (displayRectangle.width() * 0.9f).toInt()
        dialog = Dialog(mainActivity)



        mAddMore.setOnClickListener { mAddMoreFunction() }
        mConfirmCar.setOnClickListener { thankuDialog() }
        user_id = FirebaseAuth.getInstance().uid
        recyclerView!!.layoutManager = LinearLayoutManager(mainActivity)

        forConfirm_progress.visibility = VISIBLE
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
                        adapter = CustomAdapter(mainActivity, notesList)
                        recyclerView!!.adapter = adapter
                    } else {
                        Toast.makeText(mainActivity, "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                    forConfirm_progress.visibility = INVISIBLE
                }


    }

    @SuppressLint("InflateParams")
    private fun thankuDialog() {
        val layout = LayoutInflater.from(mainActivity).inflate(R.layout.dialog_thanku_car, null)
        layout.minimumWidth = width
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0, 20, 0, 0)
        dialog.setContentView(layout)
        dialog.mContinueDialogCar.setOnClickListener { mConfirmCarFunction() }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }


    private fun mAddMoreFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCategories()).commit()
    }

    private fun mConfirmCarFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedServices()).commit()
        dialog.dismiss()
    }

    private fun mCarServiceBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarCategories()).commit()

    }
}
