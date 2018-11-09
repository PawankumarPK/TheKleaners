package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignIn: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRelativeLayoutSignUp.setOnClickListener { signUpFunction() }
    }

    private fun signUpFunction(){
        fragmentManager!!.beginTransaction().replace(R.id.containerView, SignUp())
                .addToBackStack(null).commit()

    }
}