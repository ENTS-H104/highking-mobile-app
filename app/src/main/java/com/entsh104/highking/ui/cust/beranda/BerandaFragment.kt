package com.entsh104.highking.ui.cust.beranda

import com.entsh104.highking.ui.adapters.BannerAdapter
import com.entsh104.highking.ui.adapters.MountainsAdapter
import com.entsh104.highking.ui.adapters.TripsAdapter
import com.entsh104.highking.ui.model.Banner
import com.entsh104.highking.ui.model.Mountain
import com.entsh104.highking.ui.model.Trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustBerandaBinding

class CustBerandaFragment : Fragment() {

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
    }

    private fun setupRecyclerViews() {
        binding.recyclerViewBanner.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMountains.layoutManager     = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTrips.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Set up adapters
        val bannerAdapter = BannerAdapter(getBannerData())
        binding.recyclerViewBanner.adapter = bannerAdapter

        val mountainsAdapter = MountainsAdapter(getMountainsData())
        binding.recyclerViewMountains.adapter = mountainsAdapter

        val tripsAdapter = TripsAdapter(getTripsData())
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
            Mountain(R.drawable.iv_mountain, "Mountain 3")
        )
    }

    private fun getTripsData(): List<Trip> {
        // Replace with your actual data fetching logic
        return listOf(
            Trip(R.drawable.iv_trip, "Trip 1", "Price 1"),
            Trip(R.drawable.iv_trip, "Trip 2", "Price 2"),
            Trip(R.drawable.iv_trip, "Trip 3", "Price 3")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

