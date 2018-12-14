package com.example.hp.thekleaners

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.hp.thekleaners.fragments.SignUpPassword

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

import java.util.concurrent.TimeUnit

class AuthActivity : AppCompatActivity() {

    private var mPhoneText: EditText? = null
    private var mCodeText: EditText? = null

    private var mPhoneBar: ProgressBar? = null
    private var mCodeBar: ProgressBar? = null

    private var mErrorText: TextView? = null
    private var mSendBtn: Button? = null

    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    private var mAuth: FirebaseAuth? = null
    private var mVerificationId: String? = null
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        mAuth = FirebaseAuth.getInstance()

        mPhoneText = findViewById<View>(R.id.phoneEditText) as EditText
        mCodeText = findViewById<View>(R.id.codeEditText) as EditText

        mPhoneBar = findViewById<View>(R.id.phoneProgress) as ProgressBar
        mCodeBar = findViewById<View>(R.id.codeProgress) as ProgressBar

        mErrorText = findViewById<View>(R.id.errorText) as TextView
        mSendBtn = findViewById<View>(R.id.sendBtn) as Button

        mSendBtn!!.setOnClickListener {
            mPhoneBar!!.visibility = View.VISIBLE
            mPhoneText!!.isEnabled = false
            mSendBtn!!.isEnabled = false

            val phoneNumber = mPhoneText!!.text.toString()

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    this@AuthActivity,
                    mCallbacks!!
            )
        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential)

            }

            override fun onVerificationFailed(e: FirebaseException) {

                mErrorText!!.text = "There is some error in verification.."
                mErrorText!!.visibility = View.VISIBLE
            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {

                mVerificationId = verificationId
                mResendToken = token

                mPhoneBar!!.visibility = View.INVISIBLE
                mCodeBar!!.visibility = View.VISIBLE

                mSendBtn!!.text = "Verify Code"
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = task.result.user

                        supportFragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SignUpPassword()).commit()
                        finish()

                    } else {

                        mErrorText!!.text = "There is some error in loggin in.."
                        mErrorText!!.visibility = View.VISIBLE

                        if (task.exception is FirebaseAuthInvalidCredentialsException) {

                        }
                    }
                }
    }
}
