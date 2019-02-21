package com.example.hp.thekleaners.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import com.example.hp.thekleaners.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_splash)
        logo!!.animation = splashAnim


        val myThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(3000)
                    val intent = Intent(applicationContext, NavigationDrawer::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
        myThread.start()

        /* Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.anim);
        logo.setAnimation(anim);
*/


    }

}
