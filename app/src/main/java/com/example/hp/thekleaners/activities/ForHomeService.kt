package com.example.hp.thekleaners.activities

import android.content.Intent
import android.os.Bundle
import com.example.hp.thekleaners.BaseClasses.BaseActivity
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.fragments.CurbsidePickup
import com.example.hp.thekleaners.fragments.MedicalWaste
import com.example.hp.thekleaners.fragments.RecyclableByMail
import com.example.hp.thekleaners.fragments.RecyclingService
import kotlinx.android.synthetic.main.fragment_for_home_service.*

class ForHomeService : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_for_home_service)

        mHomeServiceBackArrow.setOnClickListener { mHomeServiceBackArrowFunction() }
        mLinearLayoutRecycleByMail.setOnClickListener { mLinearLayoutRecycleByMailFunction() }
        mLinearLayoutCubsidePickup.setOnClickListener { mLinearLayoutCubsidePickupFunction() }
        mLinearLayoutMedical.setOnClickListener { mLinearLayoutMedicalFunction() }
        mLinearLayoutRecyclingService.setOnClickListener { mLinearLayoutRecyclingServiceFunction() }
        mForHomeContinue.setOnClickListener { mForHomeContinueFunction() }
//n

    }

    private fun mHomeServiceBackArrowFunction() {
        val intent = Intent(this@ForHomeService, NavigationDrawer::class.java)
        startActivity(intent)
    }

    private fun mLinearLayoutRecycleByMailFunction() {
        supportFragmentManager.beginTransaction().replace(R.id.mForHomeContainerFrame, RecyclableByMail()).addToBackStack(null).commit()
    }

    private fun mLinearLayoutCubsidePickupFunction() {
        supportFragmentManager.beginTransaction().replace(R.id.mForHomeContainerFrame, CurbsidePickup()).addToBackStack(null).commit()

    }

    private fun mLinearLayoutMedicalFunction() {
        supportFragmentManager.beginTransaction().replace(R.id.mForHomeContainerFrame, MedicalWaste()).addToBackStack(null).commit()

    }

    private fun mLinearLayoutRecyclingServiceFunction() {
        supportFragmentManager.beginTransaction().replace(R.id.mForHomeContainerFrame, RecyclingService()).addToBackStack(null).commit()

    }

    private fun mForHomeContinueFunction() {
        val intent = Intent(this, SignUpKleaners::class.java)
        startActivity(intent)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this@ForHomeService, NavigationDrawer::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(i)
        finish()
    }
}