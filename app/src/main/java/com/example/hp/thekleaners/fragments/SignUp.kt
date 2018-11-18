package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUp : BaseNavigationFragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.title_name.text = resources.getString(R.string.signUp)
        (activity as NavigationDrawer).setDrawerLocked(true)
        mSignUpBackArrow.setOnClickListener { signUpBackPress() }

        mRelativeLayoutSignUp.setOnClickListener { signInFunction() }

          mAuth = FirebaseAuth.getInstance()
        reg_login_btn.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.containerView, SignIn())
                    .addToBackStack(null).commit()
        }

        reg_btn.setOnClickListener {
            val email = reg_email.text.toString()
            val pass = reg_pass.text.toString()
            val confirmPass = reg_confirm_pass.text.toString()

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) and !TextUtils.isEmpty(confirmPass)) {

                if (pass == confirmPass) {

                    reg_progress.visibility = View.VISIBLE

                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {

                            sendToMain()

                        } else {

                            val errorMessage = task.exception!!.message
                            Toast.makeText(context, "Error : $errorMessage", Toast.LENGTH_LONG).show()

                        }

                        reg_progress.visibility = View.INVISIBLE
                    })

                } else {

                    Toast.makeText(context, "Confirm Password and Password Field doesn't match.", Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    override fun onStart( ) {
        super.onStart()

        val currentUser = mAuth.currentUser
        if (currentUser != null) {

            //sendToMain()

        }

    }

    private fun sendToMain() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, Home())
                .addToBackStack(null).commit()
        Toast.makeText(context, "Registration Successfully.", Toast.LENGTH_LONG).show()
    }

    private fun signInFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, SignIn())
                .addToBackStack(null).commit()
    }

        private fun signUpBackPress() {
            val intent = Intent(context, NavigationDrawer::class.java)
            startActivity(intent)
        }
    }
