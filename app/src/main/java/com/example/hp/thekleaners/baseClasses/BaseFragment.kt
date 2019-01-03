package com.example.hp.thekleaners.baseClasses


import android.os.Bundle
import android.support.v4.app.Fragment


abstract class BaseFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        homeActivity = activity as MainActivity

    }
}