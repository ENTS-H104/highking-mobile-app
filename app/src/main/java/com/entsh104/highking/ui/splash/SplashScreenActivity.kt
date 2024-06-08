package com.entsh104.highking.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.databinding.ActivitySplashScreenBinding
import com.entsh104.highking.ui.auth.AuthActivity
import com.entsh104.highking.ui.cust.CustActivity


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        prefs = SharedPreferencesManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            if (prefs.getToken() != null) {
                startActivity(Intent(this, CustActivity::class.java))
            } else {
                startActivity(Intent(this, AuthActivity::class.java))
            }
            finish()
        }, 2000)
    }
}
