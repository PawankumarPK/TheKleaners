package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.ForgotPassword
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignIn : BaseNavigationFragment() {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.title_name.text = resources.getString(R.string.signIn)

        mForgetPassword.setOnClickListener { forgotPassword()  }

        mSignInBackArrow.setOnClickListener { signInBackPress() }

        mRelativeLayoutSignUp.setOnClickListener { forSignUp()}


        login_btn.setOnClickListener {
            val loginEmail = reg_email.text.toString()
            val loginPass = reg_confirm_pass.text.toString()

            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                login_progress.visibility = View.VISIBLE

                mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {

                        sendToMain()

                    } else {

                        val errorMessage = task.exception!!.message
                        Toast.makeText(context, "Error : $errorMessage", Toast.LENGTH_LONG).show()


                    }

                    login_progress.visibility = View.INVISIBLE
                })
            }
        }
    }

    private fun sendToMain() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, Home())
                .addToBackStack(null).commit()
        Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show()
    }

    private fun signInBackPress() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)
    }

    private fun forSignUp() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, SignUp()).addToBackStack(null).commit()
    }
    private fun forgotPassword() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, ResetPassword()).addToBackStack(null).commit()
    }
}