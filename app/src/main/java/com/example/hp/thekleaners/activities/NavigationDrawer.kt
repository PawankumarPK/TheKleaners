package com.example.hp.thekleaners.activities

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import com.example.hp.thekleaners.Adapters.FragmentAdapter
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.fragments.*
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*


class NavigationDrawer : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, DrawerLocker {

    companion object {
        lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        setSupportActionBar(toolbar)
        tabLayout.visibility = View.VISIBLE
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.visibility = View.VISIBLE
        belowlayout()



        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerview = navigationView.getHeaderView(0)
        val header = headerview.findViewById(R.id.mLinearLayout) as RelativeLayout

        val pagerAdapter = FragmentAdapter(supportFragmentManager)
        mViewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(mViewPager)

        /// setupTabIcons()

        header.setOnClickListener { signInListener() }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    /*private fun setupTabIcons() {
        tabLayout.getTabAt(0)!!.setIcon(tabIcons[0])
        tabLayout.getTabAt(1)!!.setIcon(tabIcons[1])
    }
*/

    override fun setDrawerLocked(shouldLock: Boolean) {
        if (shouldLock) {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }

    }

    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(nav_view)) {
            drawer_layout.closeDrawer(GravityCompat.START)
            return
        } else (fragmentManager.backStackEntryCount == 0)
        fragmentManager.popBackStack()
        tabLayout.visibility = View.VISIBLE
        setDrawerLocked(false)
        super.onBackPressed()
    }


    private fun exitApp(exit: Boolean) {
        AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(resources.getString(R.string.exit))
                .setMessage(resources.getString(R.string.areyousureyouwanttoexit))
                .setPositiveButton(resources.getString(R.string.yes), { _, _ -> if (exit) finish() else super.onBackPressed() }).setNegativeButton(resources.getString(R.string.no), null).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_feedback -> supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.containerView, Feedback()).commit()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.mPayment -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerView, Payment()).addToBackStack(null).commit()
            }
            R.id.mLanguage -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerView, Language()).addToBackStack(null).commit()
            }
            R.id.mServices -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerView, ForHomeService()).commit()
            }
            R.id.mAboutUs -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerView, Help()).commit()
            }
            R.id.mShare -> {
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /*private fun replaceFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentById(R.id.containerView).tag != "FragmentMain")
            supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.containerView, fragment).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.containerView, fragment).commit()
    }
*/
    private fun signInListener(): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.containerView, NotUseSignIn())
                .addToBackStack(null).commit()

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun belowlayout() {
        val params = containerView.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = AppBarLayout.ScrollingViewBehavior()
        containerView.requestLayout()
    }

}
