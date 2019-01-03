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

        mSend.setOnClickListener { sendInFragment() }

    }

    private fun sendInFragment() {

        val bundle = Bundle()
        bundle.putString("NAME_KEY", pUserName.text.toString())

        //PASS OVER THE BUNDLE TO OUR FRAGMENT
        val myFragment = BlankFragment()
        myFragment.arguments = bundle

        pUserName.setText("")

        //THEN NOW SHOW OUR FRAGMENT
        supportFragmentManager.beginTransaction().replace(R.id.mFrameContainer, myFragment).commit()

    }
}