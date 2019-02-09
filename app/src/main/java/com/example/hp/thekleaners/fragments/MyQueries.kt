package com.example.hp.thekleaners.fragments


import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.design.widget.TextInputEditText
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.hp.thekleaners.dto.EmailSendDto
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import com.example.hp.thekleaners.databinding.FragmentMyqueriesBinding
import kotlinx.android.synthetic.main.fragment_myqueries.*


class MyQueries : BaseNavigationFragment() {

    private val emailSendDto = EmailSendDto()

    // private val EMAIL_REGEX = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"

    private lateinit var binding: FragmentMyqueriesBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_myqueries, container, false)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.tabLayout.visibility = View.INVISIBLE
        (activity as NavigationDrawer).setDrawerLocked(true)


        binding.emailDto = emailSendDto

        mainActivity = activity as NavigationDrawer

        mainActivity.toolbar.visibility = View.GONE

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initListener()

        mQueryBackArrow.setOnClickListener { signInBackPress() }

    }


    private fun initListener() {

        binding.submit.setOnClickListener { submitDetails() }

    }


    private fun submitDetails() {

        if (!checkNullFields())

            return

        /*if (!checkValidField())
            return
*/

        val emailIntent = Intent(Intent.ACTION_SEND)

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Query For : ${resources.getString(R.string.app_name)}")

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("pk9910765616@gmail.com"))

        emailIntent.putExtra(Intent.EXTRA_TEXT, emailData())

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


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }


    override fun onPrepareOptionsMenu(menu: Menu) {

        menu.clear()

    }


    private fun emailData(): String {

        return "Your Query : ${emailSendDto.appTitleDto}"

    }


    private fun checkNullFields(): Boolean {

        clearError()

        return when {

            emailSendDto.appTitleDto == "" -> showError(binding.mFeedback, "Query Empty")

            else -> true

        }

    }


    private fun showError(editText: TextInputEditText, error: String): Boolean {

        editText.requestFocus()

        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)

        editText.error = error

        return false

    }


    private fun clearError() {

        binding.mFeedback.error = null

    }


    private fun signInBackPress() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, Profile()).commit()

    }

}