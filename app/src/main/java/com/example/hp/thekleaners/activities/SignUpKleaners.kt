package com.example.hp.thekleaners.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.hp.thekleaners.BaseClasses.ForHomeServiceBaseFragment
import com.example.hp.thekleaners.MainActivity
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.VerifiyPhoneActivity
import com.example.hp.thekleaners.fragments.NumberVerification
import com.example.hp.thekleaners.fragments.Profile
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_signup_kleaners.*

class SignUpKleaners : ForHomeServiceBaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup_kleaners, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        fragmentManager!!.beginTransaction().replace(R.id.mForHomeContainerFrame, newFragment).commit()

    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {
            fragmentManager!!.beginTransaction().replace(R.id.mForHomeContainerFrame, Profile()).commit()
        }
    }
}
