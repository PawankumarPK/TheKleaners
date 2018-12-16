package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.BaseClasses.HomeBaseFragment
import com.example.hp.thekleaners.MainActivity
import com.example.hp.thekleaners.R
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_verification.*
import java.util.concurrent.TimeUnit
import android.content.Intent.getIntent
import android.net.Uri
import android.content.Intent.getIntent
import android.content.Intent.getIntent






class Verification : HomeBaseFragment() {

    private var verificationId: String? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        buttonSignIn.setOnClickListener(View.OnClickListener {
            val code = editTextCode.getText().toString().trim()


            if (code.isEmpty() || code.length < 6) {
                editTextCode.error = "Enter code..."
                editTextCode.requestFocus()
                return@OnClickListener
            }

            verifyCode(code)
        })

        mAuth = FirebaseAuth.getInstance()




                //  val uri = activity!!.intent.data.toString()
        val phonenumber = getIntent("phonenumber").toString()
        sendVerificationCode(phonenumber)

    }

    private fun verifyCode(code: String?) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code!!)
        signInWithCredential(credential)

    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential).addOnCompleteListener({ task ->
            if (task.isSuccessful) {

                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } else {
                Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendVerificationCode(number: String) {
        progressbar.visibility = View.VISIBLE
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        )
    }

    private val mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        override fun onCodeSent(s: String?, forceResendingToken: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(s, forceResendingToken)
            verificationId = s
        }

        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

            val code = phoneAuthCredential.smsCode
            if (code != null) {
                editTextCode.setText(code)
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {

            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}

