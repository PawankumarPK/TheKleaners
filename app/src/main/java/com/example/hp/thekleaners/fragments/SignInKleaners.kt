package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat.setNestedScrollingEnabled
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.BaseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_signin_kleaners.*

class SignInKleaners : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin_kleaners, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        setNestedScrollingEnabled(mNestedScrollView,false)
        (activity as NavigationDrawer).setDrawerLocked(true)
      //  mForSignUpClick.setOnClickListener { mForSignUpClickFunction() }
        mContinueSignIn.setOnClickListener { mContinueSignInFunction() }
        mSignInBackButton.setOnClickListener { mMobileVerBackButtonFunction() }
    }


    private fun mMobileVerBackButtonFunction() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)

    }

  /*  private fun mForSignUpClickFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SignUpKleaners()).commit()
    }
*/

    private fun mContinueSignInFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()
    }

}