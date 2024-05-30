package com.entsh104.highking.ui.cust

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.entsh104.highking.databinding.ActivityCustBinding

class CustActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCustBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}