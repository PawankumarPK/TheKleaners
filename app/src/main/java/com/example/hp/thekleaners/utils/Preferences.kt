package com.example.hp.thekleaners.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.hp.thekleaners.R


class Preferences {

    private lateinit var sharedPreferences: SharedPreferences
    private var instance: Preferences? = null


    var homeAndFlat: Boolean
        get() = sharedPreferences.getBoolean(HOMEANDFLATS, false)
        set(value) = sharedPreferences.edit().putBoolean(HOMEANDFLATS, value).apply()

    var farmHouse: Boolean
        get() = sharedPreferences.getBoolean(FARMHOUSE, true)
        set(value) = sharedPreferences.edit().putBoolean(FARMHOUSE, value).apply()


    private fun initialize(context: Context): Preferences {
        this.sharedPreferences = context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        return this
    }

    fun getInstance(context: Context): Preferences {
        if (instance == null)
            instance = initialize(context)
        return instance!!
    }

    companion object {
        private const val HOMEANDFLATS = "HOMEANDFLATS"
        private const val FARMHOUSE = "FARMHOUSE"

    }


}
