package com.example.hp.thekleaners

import android.os.Bundle
import com.example.hp.thekleaners.baseClasses.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*   mSend.setOnClickListener {
             supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mContainer,BundleFragment()).commit()
             intent.putExtra("name", pUserName.text.toString())
            // intent.putExtra("password", pPassword.text.toString())
             startActivity(intent)

         }*/


    }
}