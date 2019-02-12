package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_helpcenter.*

class HelpCenter : BaseNavigationFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_helpcenter, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        mCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:9910765616")
            startActivity(intent)
        }

        mChat.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "9910765616", null))
            intent.putExtra("sms_body", "TheKleaners:  ")
            startActivity(intent)
        }

        mSendMail.setOnClickListener { sendMail() }
        mSendFeedback.setOnClickListener { mSendFeedbackFunction() }
        mHelpBackArrow.setOnClickListener { mHelpBackArrowFunction() }

    }

    private fun sendMail() {
        val emailIntent = Intent(Intent.ACTION_SEND)

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help Center : ${resources.getString(R.string.app_name)}")

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("info@thekleaners.com"))

        emailIntent.type = "text/plain"

        val matches = context!!.packageManager.queryIntentActivities(emailIntent, 0)

        var best: ResolveInfo? = null

        for (info in matches)

            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))

                best = info

        if (best != null)

            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name)


        startActivity(emailIntent)

    }

    private fun mSendFeedbackFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Feedback()).commit()
    }

    private fun mHelpBackArrowFunction() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)
    }
}

