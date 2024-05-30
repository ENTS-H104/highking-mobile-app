package com.entsh104.highking.ui.auth.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.entsh104.highking.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}