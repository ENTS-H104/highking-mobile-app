package com.entsh104.highking.ui.cust.beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustBerandaBinding
import com.entsh104.highking.ui.adapters.BannerAdapter
import com.entsh104.highking.ui.adapters.MountainAdapter
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

        // Set up click listener for "Lihat Semua" in Rekomendasi Trip section
        binding.root.findViewById<TextView>(R.id.rekomendasi_trip_lihat_semua).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_listTrip)
        }
        binding.root.findViewById<TextView>(R.id.gunung_terpopuler_lihat_semua).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_listMountain)
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

        val mountainsAdapter = MountainAdapter(getMountainsData(), true)
        binding.recyclerViewMountains.adapter = mountainsAdapter

        val tripsAdapter = TripsAdapter(getTripsData(), true)
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
            Mountain(R.drawable.iv_mountain, "Bromo", 2329, "Probolinggo, Jawa Timur", 9, false),
            Mountain(R.drawable.iv_mountain, "Semeru", 3676, "Lumajang, Jawa Timur", 5, true),
            Mountain(R.drawable.iv_mountain, "Merapi", 2355, "Boyolali, Jawa Tengah", 10, true),
            Mountain(R.drawable.iv_mountain, "Rinjani", 3726, "Lombok, NTB", 7, false)
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
