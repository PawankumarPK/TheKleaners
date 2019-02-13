package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_number_verification.*
import java.util.concurrent.TimeUnit

class NumberVerification : BaseNavigationFragment() {

    private var verificationId: String? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_number_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        //mainActivity.title_name.text = resources.getString(R.string.signIn)
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        mNumberVerficationBackArrow.setOnClickListener { mPasswordBackArrowFunction() }

        buttonSignIn.setOnClickListener(View.OnClickListener {
            val code = editTextCode.text.toString().trim()
            if (code.isEmpty() || code.length < 6) {
                editTextCode.error = "Enter valid code..."
                editTextCode.requestFocus()
                return@OnClickListener
            }
            verifyCode(code)
        })

        mAuth = FirebaseAuth.getInstance()

        val phonenumber = this.arguments!!.getString("phonenumber").toString()
        sendVerificationCode(phonenumber)

    }

    /* private fun sendNumberToProfile(){
         val bundle = Bundle()
        bundle.putString("phonenumber",mGetBundleData.toString())
         val myFragment = Profile()
         myFragment.arguments = bundle
        // pUserName.setText("")


     }
 */
    private fun verifyCode(code: String?) {

            val credential = PhoneAuthProvider.getCredential(verificationId!!, code!!)
            signInWithCredential(credential)


    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                //  sendNumberToProfile()
                fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()
                Toast.makeText(context, "Successfully Verification", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendVerificationCode(number: String) {
        progressbar.visibility = View.VISIBLE
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91$number",
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

            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mPasswordBackArrowFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, SignUpKleaners()).addToBackStack(null).commit()
    }
}

