package com.entsh104.highking.ui.model

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.ui.adapters.BannerAdapter
import com.entsh104.highking.ui.adapters.MountainAdapter
import com.entsh104.highking.ui.adapters.TripAdapter

class CustActivity : AppCompatActivity() {
    private var recyclerViewBanner: RecyclerView? = null
    private var recyclerViewMountains: RecyclerView? = null
    private var recyclerViewTrips: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cust)
        recyclerViewBanner = findViewById<RecyclerView>(R.id.recyclerViewBanner)
        recyclerViewMountains = findViewById<RecyclerView>(R.id.recyclerViewMountains)
        recyclerViewTrips = findViewById<RecyclerView>(R.id.recyclerViewTrips)

        // Setup Banner RecyclerView
        recyclerViewBanner.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        val bannerImages: MutableList<Int> = ArrayList()
        bannerImages.add(R.drawable.banner1)
        bannerImages.add(R.drawable.banner2)
        val bannerAdapter = BannerAdapter(bannerImages)
        recyclerViewBanner.setAdapter(bannerAdapter)

        // Setup Mountains RecyclerView
        recyclerViewMountains.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        val mountains: MutableList<Mountain> = ArrayList<Mountain>()
        mountains.add(Mountain(R.drawable.mountain_rinjani, "Rinjani"))
        mountains.add(Mountain(R.drawable.mountain_merbabu, "Merbabu"))
        mountains.add(Mountain(R.drawable.mountain_merapi, "Merapi"))
        val mountainAdapter = MountainAdapter(mountains)
        recyclerViewMountains.setAdapter(mountainAdapter)

        // Setup Trips RecyclerView
        recyclerViewTrips.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        val trips: MutableList<Trip> = ArrayList<Trip>()
        trips.add(Trip(R.drawable.trip_kencana, "Trip Kencana", "Rp 500.000"))
        trips.add(Trip(R.drawable.trip_bali_rinai, "Trip Bali Rinai", "Rp 200.000"))
        trips.add(Trip(R.drawable.trip_kencana, "Trip Kencana", "Rp 1.200.000"))
        val tripAdapter = TripAdapter(trips)
        recyclerViewTrips.setAdapter(tripAdapter)
    }
}
