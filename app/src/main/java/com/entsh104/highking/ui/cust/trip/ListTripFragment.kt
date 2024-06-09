package com.entsh104.highking.ui.cust.trip

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.entsh104.highking.data.model.TripFilter
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustListTripBinding
import com.entsh104.highking.ui.adapters.TripsAdapter

class ListTripFragment : Fragment() {

    private var _binding: FragmentCustListTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustListTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = SharedPreferencesManager(requireContext())
            RetrofitClient.createInstance(requireContext()) 
    userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewTrips.layoutManager = gridLayoutManager
        binding.recyclerViewTrips.addItemDecoration(GridSpacingItemDecoration(2, 1, true))

        arguments?.let {
            val searchResults = ListTripFragmentArgs.fromBundle(it).searchResults
            displayTrips(searchResults.toList())
        }
    }

    private fun displayTrips(trips: List<TripFilter>) {
        val tripsAdapter = TripsAdapter(trips)
        binding.recyclerViewTrips.adapter = tripsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
