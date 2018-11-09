package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUp : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRelativeLayoutSignUp.setOnClickListener { signInFunction() }
    }

    private fun signInFunction(){
        fragmentManager!!.beginTransaction().replace(R.id.containerView, SignIn())
                .addToBackStack(null).commit()
    }
}