package com.utn.apputnerds.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.utn.apputnerds.R

private val SPLASH_TIME_OUT:Long = 2000 // 3 sec

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(

            {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            , SPLASH_TIME_OUT)
    }
}