package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
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

        mMobileVerBackButton.setOnClickListener { mMobileVerBackButtonFunction() }

        buttonContinue!!.setOnClickListener{
            val number = editTextPhone!!.text.toString().trim()
            /*if (number.isEmpty() || number.length < 17) {
                editTextPhone!!.error = "Valid Number is required"
                editTextPhone!!.requestFocus()
                return@OnClickListener
            }*/

/*
            val intent = Intent(this@AuthActivity, VerifiyPhoneActivity::class.java)
            intent.putExtra("phonenumber", number)
            startActivity(intent)*/

            sendToVerification()
        }

    }

    private fun sendToVerification() {
        val args = Bundle()
        args.putString("phonenumber", editTextPhone.text.toString())
        val newFragment = NumberVerification()
        newFragment.arguments = args
      //  mRelativeLayoutForGoneSignUp.visibility = View.GONE
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, newFragment).commit()

    }

   /* override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(context, AuthActivity::class.java)
            startActivity(intent)
        }
    }*/

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {
            mRelativeLayoutForGoneSignUp.visibility = View.GONE
            fragmentManager!!.beginTransaction().replace(R.id.containerView, Profile()).commit()
        }
    }
    private fun mMobileVerBackButtonFunction() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)
    }

}
