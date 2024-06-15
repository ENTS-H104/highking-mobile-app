// ui/cust/trip/ListTripFragment.kt
package com.entsh104.highking.ui.cust.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.data.viewmodel.TripsViewModel
import com.entsh104.highking.data.viewmodel.TripsViewModelFactory
import com.entsh104.highking.databinding.FragmentCustListAllTripBinding
import com.entsh104.highking.ui.adapters.TripsPagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListAllTripFragment : Fragment() {

    private var _binding: FragmentCustListAllTripBinding? = null
    private val binding get() = _binding!!
    private val tripsViewModel: TripsViewModel by viewModels {
        TripsViewModelFactory(RetrofitClient.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustListAllTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewTrips.layoutManager = gridLayoutManager
        binding.recyclerViewTrips.addItemDecoration(GridSpacingItemDecoration(2, 1, true))

        val tripsAdapter = TripsPagingAdapter()
        binding.recyclerViewTrips.adapter = tripsAdapter

        lifecycleScope.launch {
            tripsViewModel.trips.collectLatest { pagingData ->
                tripsAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
