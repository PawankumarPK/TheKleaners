package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_terms_of_uses.*


class TermsOfUses : BaseNavigationFragment() {

    private val urlSearch = "http://thekleaners.com/terms-of-use.html"
    //private var webView: WebView? = null
    private var progressDialog: ProgressDialog? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_terms_of_uses, container, false)


    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        mVisitSiteBackButton.setOnClickListener { mVisitSiteBackButtonFunction() }


        //   val webView = findViewById(R.id.webView) as WebView

        progressDialog = ProgressDialog(mainActivity)
        progressDialog!!.max = 100
        progressDialog!!.setMessage("Please Wait")
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.progress = 0
        progressDialog!!.setCancelable(false)

        //loading webview
        webView!!.settings.javaScriptEnabled = true
        webView!!.loadUrl(urlSearch)
        webView!!.isHorizontalScrollBarEnabled = true

        //for Zoom in of webpage
        /* webView!!.settings.setSupportZoom(true)
         webView!!.settings.builtInZoomControls = true
         webView!!.settings.displayZoomControls = true
 */
        //for the progress dialog to appear
        webView!!.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(View: WebView, progress: Int) {

                progressDialog!!.progress = progress
                if (progress == 100) {
                    progressDialog!!.dismiss()
                } else {
                    progressDialog!!.show()
                }
            }
        }

    }

    private fun mVisitSiteBackButtonFunction() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)
    }
}

