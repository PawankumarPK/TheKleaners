package com.example.hp.thekleaners

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

import java.util.concurrent.TimeUnit

class VerifiyPhoneActivity : AppCompatActivity() {

    private var verificationId: String? = null
    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    private var editText: EditText? = null
    private var button: Button? = null

    private val mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        override fun onCodeSent(s: String?, forceResendingToken: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(s, forceResendingToken)
            verificationId = s
        }

        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

            val code = phoneAuthCredential.smsCode
            if (code != null) {
                editText!!.setText(code)
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {

            Toast.makeText(this@VerifiyPhoneActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifiy_phone)

        progressBar = findViewById<View>(R.id.progressbar) as ProgressBar
        button = findViewById<View>(R.id.buttonSignIn) as Button
        editText = findViewById<View>(R.id.editTextCode) as EditText

        button!!.setOnClickListener(View.OnClickListener {
            val code = editText!!.text.toString().trim { it <= ' ' }

            if (code.isEmpty() || code.length < 6) {
                editText!!.error = "Enter code..."
                editText!!.requestFocus()
                return@OnClickListener
            }

            verifyCode(code)
        })

        mAuth = FirebaseAuth.getInstance()
        val phonenumber = intent.getStringExtra("phonenumber")
        sendVerificationCode(phonenumber)

    }

    private fun verifyCode(code: String?) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code!!)
        signInWithCredential(credential)

    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val intent = Intent(this@VerifiyPhoneActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } else {
                Toast.makeText(this@VerifiyPhoneActivity, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendVerificationCode(number: String) {
        progressBar!!.visibility = View.VISIBLE
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        )
    }
}
