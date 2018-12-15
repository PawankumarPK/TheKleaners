package com.example.hp.thekleaners.activities

import android.os.Bundle
import android.view.View
import com.example.hp.thekleaners.BaseClasses.BaseActivity
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.fragments.SignInKleaners
import com.example.hp.thekleaners.fragments.SignUpPassword
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_signup_kleaners.*
import java.util.concurrent.TimeUnit
import android.content.Intent



class SignUpKleaners : BaseActivity() {

    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mAuth: FirebaseAuth? = null
    private var mVerificationId: String? = null
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_signup_kleaners)

        mAuth = FirebaseAuth.getInstance()

        mMobileVerBackButton.setOnClickListener { mMobileVerBackButtonFunction() }
        mForSignInClick.setOnClickListener { mForSignInClickFunction() }
        sendBtn.setOnClickListener { sendBtnFunction() }
    }

    private fun sendBtnFunction() {
        phoneProgress!!.visibility = View.VISIBLE
        phoneEditText!!.isEnabled = false
        sendBtn.isEnabled = false

        val phoneNumber = phoneEditText.text.toString()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this@SignUpKleaners,
                mCallbacks!!)

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                errorText.text = "There is some error in verification.."
                errorText.visibility = View.VISIBLE
            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {

                mVerificationId = verificationId
                mResendToken = token

                phoneProgress.visibility = View.INVISIBLE
                //  mCodeBar!!.visibility = View.VISIBLE

                sendBtn.text = "Verify Code"
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = task.result.user

                        supportFragmentManager.beginTransaction().replace(R.id.mSignUpFrameContainer, SignUpPassword()).commit()

                    } else {

                        errorText!!.text = "There is some error in loggin in.."
                        errorText!!.visibility = View.VISIBLE

                        if (task.exception is FirebaseAuthInvalidCredentialsException) {

                        }
                    }
                }
    }

    private fun mMobileVerBackButtonFunction() {
        val intent = Intent(this, ForHomeService::class.java)
        startActivity(intent)
    }

    private fun mForSignInClickFunction() {
        supportFragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.mSignUpFrameContainer, SignInKleaners()).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this@SignUpKleaners, ForHomeService::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(i)
        finish()
    }


}
