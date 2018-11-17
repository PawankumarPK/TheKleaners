package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_reset_password.*

class ResetPassword : BaseNavigationFragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        mSendLinkForReset.setOnClickListener {
            val userEmail = mEnterRegisterEmailAdd.text.toString()
            if (TextUtils.isEmpty(userEmail)) {
                Toast.makeText(context, "Please write your email address first", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Please check your email account ", Toast.LENGTH_SHORT).show()
                        fragmentManager!!.beginTransaction().replace(R.id.containerView, SignIn()).addToBackStack(null).commit()
                    } else {
                        val message = task.exception!!.message
                        Toast.makeText(context, "Error Occurred: $message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}
