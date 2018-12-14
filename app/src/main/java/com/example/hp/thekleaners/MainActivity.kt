package com.example.hp.thekleaners

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.hp.thekleaners.fragments.LanguageHomePage
import com.example.hp.thekleaners.fragments.SignUpPassword
import com.google.firebase.auth.PhoneAuthProvider


class MainActivity : AppCompatActivity() {

    var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragementHome()

    }

    private fun showFragementHome() {
        supportFragmentManager.beginTransaction().replace(R.id.mFrameContainer, SignUpPassword())
                .commit()
    }
}
