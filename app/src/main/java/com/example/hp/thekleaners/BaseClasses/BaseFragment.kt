package com.example.hp.thekleaners.BaseClasses

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.hp.thekleaners.MainActivity


abstract class BaseFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        homeActivity = activity as MainActivity

    }
}