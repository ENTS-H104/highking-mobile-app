package com.entsh104.highking.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.entsh104.highking.databinding.ActivitySplashScreenBinding
import com.entsh104.highking.ui.auth.AuthActivity
import com.entsh104.highking.ui.cust.CustActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                startActivity(Intent(this, CustActivity::class.java))
                finish()
            }, 2000)
        }
    }
}