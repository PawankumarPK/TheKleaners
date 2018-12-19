package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.BaseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import kotlinx.android.synthetic.main.fragment_user_edit_profile.*

class UserEditProfile : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSubmit.setOnClickListener { mSubmitFunction() }
        mEditProfileBackArrow.setOnClickListener { mEditProfileBackArrowFunction() }

    }

    private fun mSubmitFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()
    }


    private fun mEditProfileBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()
    }

}
