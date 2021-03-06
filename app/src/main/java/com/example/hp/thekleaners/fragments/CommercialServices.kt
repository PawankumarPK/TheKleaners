package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.R
import kotlinx.android.synthetic.main.fragment_commercial_services.*

class CommercialServices : BaseNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_commercial_services,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMedicalCardView.setOnClickListener { upComing() }
        mPartyCardView.setOnClickListener { upComing() }
        mConstructionCardView.setOnClickListener { upComing() }
    }

    private fun upComing(){
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, UpcomingServices()).commit()
    }
}