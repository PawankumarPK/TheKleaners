package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.BaseClasses.HomeBaseFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.activities.SignUpKleaners
import kotlinx.android.synthetic.main.fragment_signin_kleaners.*

class SignInKleaners : HomeBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin_kleaners, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mForSignUpClick.setOnClickListener { mForSignUpClickFunction() }
        mContinueSignIn.setOnClickListener { mContinueSignInFunction() }
        mSignInBackButton.setOnClickListener { mMobileVerBackButtonFunction() }
    }


    private fun mMobileVerBackButtonFunction() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)

    }

    private fun mForSignUpClickFunction() {
        val intent = Intent(context, SignUpKleaners::class.java)
        startActivity(intent)
    }

    private fun mContinueSignInFunction() {
      //  fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.mSignUpFrameContainer, Profile()).commit()
    }

}