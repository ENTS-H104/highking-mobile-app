package com.entsh104.highking.ui.cust

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.entsh104.highking.BuildConfig
import com.entsh104.highking.R
import com.entsh104.highking.databinding.ActivityCustBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.midtrans.sdk.uikit.external.UiKitApi

class CustActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarCust)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_cust) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_chat, R.id.nav_search, R.id.nav_orders, R.id.nav_profile)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

        UiKitApi.Builder()
            .withMerchantClientKey(
                BuildConfig.MIDTRANS_CLIENT_KEY
            )
            .withContext(applicationContext)
            .withMerchantUrl(BuildConfig.MIDTRANS_URL)
            .enableLog(true)
            .build()
    }

    fun hideToolbar() {
        supportActionBar?.hide()
    }

    fun showToolbar() {
        supportActionBar?.show()
    }
    fun hideToolbarAndNavbar() {
        supportActionBar?.hide()
        findViewById<View>(R.id.bottomNavigationView)?.visibility = View.GONE
    }

    fun showToolbarAndNavbar() {
        supportActionBar?.show()
        findViewById<View>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
