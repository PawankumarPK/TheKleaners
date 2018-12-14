package com.example.hp.thekleaners.BaseClasses

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.hp.thekleaners.activities.SignUpKleaners


abstract class HomeBaseFragment : Fragment() {


    lateinit var homeActivity : SignUpKleaners

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeActivity = activity as SignUpKleaners

    }
}