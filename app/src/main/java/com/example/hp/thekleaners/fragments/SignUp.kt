package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUp : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          mainActivity = activity as NavigationDrawer
          mainActivity.title_name.text = resources.getString(R.string.signUp)
          mRelativeLayoutSignUp.setOnClickListener { signInFunction() }
    }

    private fun signInFunction() {
        fragmentManager!!.beginTransaction().replace(R.id.containerView, SignIn())
                .addToBackStack(null).commit()
    }
}