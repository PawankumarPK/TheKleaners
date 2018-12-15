package com.example.hp.thekleaners.BaseClasses

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.hp.thekleaners.activities.ForHomeService


abstract class ForHomeServiceBaseFragment : Fragment() {


    lateinit var homeServiceActivity: ForHomeService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeServiceActivity = activity as ForHomeService

    }
}