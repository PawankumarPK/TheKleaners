package com.example.hp.thekleaners.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.hp.thekleaners.BaseClasses.BaseActivity
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.VerifiyPhoneActivity
import com.example.hp.thekleaners.fragments.Profile
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

            val intent = Intent(this@SignUpKleaners, VerifiyPhoneActivity::class.java)
            intent.putExtra("phonenumber", number)
            startActivity(intent)

        })


    }
    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {
            supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mSignUpFrameContainer, Profile()).commit()
            /*intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)*/
        }
    }
}
