package com.entsh104.highking.ui.cust.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustListTripBinding
import com.entsh104.highking.ui.adapters.TripsAdapter
import com.entsh104.highking.ui.model.Trip

class ListTripFragment : Fragment() {

    private var _binding: FragmentCustListTripBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustListTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView with GridLayoutManager
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewTrips.layoutManager = gridLayoutManager
        binding.recyclerViewTrips.addItemDecoration(GridSpacingItemDecoration(2, 1, true))

        binding.recyclerViewTrips.adapter = TripsAdapter(getTripsData())
    }

    private fun getTripsData(): List<Trip> {
        // Replace with your actual data fetching logic
        return listOf(
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            // Add more trips as needed
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
