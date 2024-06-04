package com.entsh104.highking.ui.cust.beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustBerandaBinding
import com.entsh104.highking.ui.adapters.BannerAdapter
import com.entsh104.highking.ui.adapters.MountainsAdapter
import com.entsh104.highking.ui.adapters.TripsAdapter
import com.entsh104.highking.ui.model.Banner
import com.entsh104.highking.ui.model.Mountain
import com.entsh104.highking.ui.model.Trip

class BerandaFragment : Fragment() {

    private var _binding: FragmentCustBerandaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerViews
        setupRecyclerViews()

        // toolbar customize to custom action bar beranda
        // Customize the ActionBar
//        (activity as AppCompatActivity).supportActionBar?.apply {
//            setDisplayShowTitleEnabled(false)
//            customView = LayoutInflater.from(context).inflate(R.layout.custom_action_bar_beranda, null)
//            setDisplayShowCustomEnabled(true)
//        }


        // Set up click listener for "Lihat Semua" in Rekomendasi Trip section
        binding.root.findViewById<TextView>(R.id.rekomendasi_trip_lihat_semua).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_listTrip)
        }
    }

    private fun setupRecyclerViews() {
        binding.recyclerViewBanner.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMountains.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Use GridLayoutManager with 2 columns for horizontal RecyclerView
        val tripsLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTrips.layoutManager = tripsLayoutManager

        // Set up adapters
        val bannerAdapter = BannerAdapter(getBannerData())
        binding.recyclerViewBanner.adapter = bannerAdapter

        val mountainsAdapter = MountainsAdapter(getMountainsData())
        binding.recyclerViewMountains.adapter = mountainsAdapter

        val tripsAdapter = TripsAdapter(getTripsData(), true) // Pass true to indicate horizontal layout
        binding.recyclerViewTrips.adapter = tripsAdapter
    }

    private fun getBannerData(): List<Banner> {
        // Replace with your actual data fetching logic
        return listOf(
            Banner(R.drawable.iv_banner),
            Banner(R.drawable.iv_banner),
            Banner(R.drawable.iv_banner)
        )
    }

    private fun getMountainsData(): List<Mountain> {
        // Replace with your actual data fetching logic
        return listOf(
            Mountain(R.drawable.iv_mountain, "Mountain 1"),
            Mountain(R.drawable.iv_mountain, "Mountain 2"),
            Mountain(R.drawable.iv_mountain, "Mountain 3"),
            Mountain(R.drawable.iv_mountain, "Mountain 4"),
            Mountain(R.drawable.iv_mountain, "Mountain 5"),
            Mountain(R.drawable.iv_mountain, "Mountain 6")
        )
    }

    private fun getTripsData(): List<Trip> {
        // Replace with your actual data fetching logic
        return listOf(
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 99)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
