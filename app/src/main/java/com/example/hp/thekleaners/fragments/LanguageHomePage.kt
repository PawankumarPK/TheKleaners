package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import kotlinx.android.synthetic.main.fragment_language_choose.*

class LanguageHomePage : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContinue.setOnClickListener { continueButton() }
    }

    private fun continueButton() {
        fragmentManager!!.beginTransaction().replace(R.id.mFrameContainer, LanguageChoose())
                .addToBackStack(null).commit()

    }
}