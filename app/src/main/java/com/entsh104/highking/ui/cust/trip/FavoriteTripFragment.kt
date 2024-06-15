// ui/cust/trip/FavoriteTripFragment.kt
package com.entsh104.highking.ui.cust.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.data.mapper.TripMapper
import com.entsh104.highking.data.viewmodel.TripViewModel
import com.entsh104.highking.databinding.FragmentCustFavTripBinding
import com.entsh104.highking.ui.adapters.TripsAdapter

class FavoriteTripFragment : Fragment() {

    private var _binding: FragmentCustFavTripBinding? = null
    private val binding get() = _binding!!
    private val tripViewModel: TripViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustFavTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TripsAdapter(emptyList(), viewModel = tripViewModel)
        binding.recyclerViewFavoriteTrips.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavoriteTrips.adapter = adapter

        tripViewModel.favoriteTrips.observe(viewLifecycleOwner) { trips ->
            val tripFilters = trips.map { TripMapper.mapEntityToFilter(it) }
            adapter.trips = tripFilters
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
