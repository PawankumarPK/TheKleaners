package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.BaseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_signup_kleaners.*

class SignUpKleaners : BaseNavigationFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup_kleaners, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        //mainActivity.title_name.text = resources.getString(R.string.signIn)
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        buttonContinue!!.setOnClickListener(View.OnClickListener {
            val number = editTextPhone!!.text.toString().trim()

            if (number.isEmpty() || number.length < 10) {
                editTextPhone!!.error = "Valid Number is required"
                editTextPhone!!.requestFocus()
                return@OnClickListener
            }
            sendToVerification()
/*
            val intent = Intent(this@AuthActivity, VerifiyPhoneActivity::class.java)
            intent.putExtra("phonenumber", number)
            startActivity(intent)*/
        })

    }

    private fun sendToVerification() {
        val args = Bundle()
        args.putString("phonenumber", editTextPhone.text.toString())
        val newFragment = NumberVerification()
        newFragment.arguments = args
        mRelativeLayoutForGoneSignUp.visibility = View.GONE
        fragmentManager!!.beginTransaction().addToBackStack("second frag").replace(R.id.mForHomeContainerFrame, newFragment).commit()

    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {
            mRelativeLayoutForGoneSignUp.visibility = View.GONE
            fragmentManager!!.beginTransaction().replace(R.id.mForHomeContainerFrame, Profile()).commit()
        }
    }
}
