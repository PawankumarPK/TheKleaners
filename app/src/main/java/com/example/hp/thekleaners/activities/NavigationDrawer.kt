package com.example.hp.thekleaners.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.fragments.*
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*


class NavigationDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerview = navigationView.getHeaderView(0)
        val header = headerview.findViewById(R.id.mLinearLayout) as LinearLayout

        header.setOnClickListener {
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
            signInListener()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.mHome -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerView, Home()).commit()
            }
            R.id.mLanguage -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerView, Language()).commit()
            }
            R.id.mServices -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerView, Services()).commit()
            }
            R.id.mHelp -> {
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
    private fun signInListener():Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.containerView, SignIn())
                .addToBackStack(null).commit()

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
