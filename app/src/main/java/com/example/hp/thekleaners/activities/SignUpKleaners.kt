package com.example.hp.thekleaners.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.hp.thekleaners.BaseClasses.BaseActivity
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.VerifiyPhoneActivity
import com.example.hp.thekleaners.fragments.NumberVerification
import com.example.hp.thekleaners.fragments.Profile
import com.example.hp.thekleaners.fragments.UserEditProfile
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_signup_kleaners.*


class SignUpKleaners : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_signup_kleaners)

        buttonContinue.setOnClickListener(View.OnClickListener {
            val number = editTextPhone.text.toString().trim()

            if (number.isEmpty() || number.length < 10) {
                editTextPhone.error = "Valid Number is required"
                editTextPhone.requestFocus()
                return@OnClickListener
            }
            sendNumberToProfile()

            /*intent.putExtra("phonenumber", number)
            startActivity(intent)*/

        })


    }

    private fun sendNumberToProfile(){
        val bundle = Bundle()
        bundle.putString("phonenumber",editTextPhone.text.toString())
        val myFragment = Profile()
        myFragment.arguments = bundle
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mSignUpFrameContainer, Profile()).commit()
        // pUserName.setText("")


    }

    /* override fun onStart() {
         super.onStart()

         if (FirebaseAuth.getInstance().currentUser != null) {
             supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mSignUpFrameContainer, Profile()).commit()
             *//*intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)*//*
        }
    }*/
}
