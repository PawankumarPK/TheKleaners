package com.example.hp.thekleaners.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import com.example.hp.thekleaners.Adapters.FragmentAdapter
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.fragments.*
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*


class NavigationDrawer : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, DrawerLocker {
    override fun setDrawerLocked(enabled: Boolean) {
        if (enabled) {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        setSupportActionBar(toolbar)
        toolbar.visibility = View.VISIBLE
       // homeFragment()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerview = navigationView.getHeaderView(0)
        val header = headerview.findViewById(R.id.mLinearLayout) as RelativeLayout

        //val vp_pages = findViewById<View>(R.id.vp_pages) as ViewPager
        val pagerAdapter = FragmentAdapter(supportFragmentManager)
        vp_pages.adapter = pagerAdapter

        //val tbl_pages = findViewById<View>(R.id.tbl_pages) as TabLayout
        tbl_pages.setupWithViewPager(vp_pages)

        header.setOnClickListener { signInListener() }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val backstack = supportFragmentManager.backStackEntryCount
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else if (backstack > 0) {
            for (i in 0 until backstack) {
                supportFragmentManager.popBackStackImmediate()
            }
        } else {
            this.finish()
        }
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
                supportFragmentManager.beginTransaction().replace(R.id.containerView, Payment()).commit()
            }
            R.id.mLanguage -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerView, Language()).commit()
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
        supportFragmentManager.beginTransaction().replace(R.id.containerView, SignIn())
                .addToBackStack(null).commit()

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

   /* private fun homeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.containerView, Home()).addToBackStack(null)
                .commit()
    }*/

}
