package com.example.hp.thekleaners.BaseClasses

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.PhoneAuthProvider

abstract class BaseActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}