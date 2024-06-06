package com.entsh104.highking.ui.cust.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustMitraProfileTripBinding
import com.entsh104.highking.ui.adapters.TripsAdapter
import com.entsh104.highking.ui.model.Trip

class TripFragment : Fragment() {

    private var _binding: FragmentCustMitraProfileTripBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustMitraProfileTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewTrip.layoutManager = LinearLayoutManager(requireContext())
        // Setup adapter here
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewTrip.layoutManager = gridLayoutManager
        binding.recyclerViewTrip.addItemDecoration(GridSpacingItemDecoration(2, 1, true))
        binding.recyclerViewTrip.adapter = TripsAdapter(getTripsData())

    }

    private fun getTripsData(): List<Trip> {
        return listOf(
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 15/30),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 15/30),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 15/30),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 15/30),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 15/30),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 15/30)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
