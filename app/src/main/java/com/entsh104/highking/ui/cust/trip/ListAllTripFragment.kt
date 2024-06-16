// ui/cust/trip/ListTripFragment.kt
package com.entsh104.highking.ui.cust.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.data.viewmodel.TripViewModel
import com.entsh104.highking.data.viewmodel.TripsViewModel
import com.entsh104.highking.data.viewmodel.TripsViewModelFactory
import com.entsh104.highking.databinding.FragmentCustListAllTripBinding
import com.entsh104.highking.ui.adapters.TripsPagingAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListAllTripFragment : Fragment() {

    private var _binding: FragmentCustListAllTripBinding? = null
    private val binding get() = _binding!!
    private val tripsViewModel: TripsViewModel by viewModels {
        TripsViewModelFactory(RetrofitClient.getInstance())
    }
    private val tripViewModel2: TripViewModel by viewModels()

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

        val tripsAdapter = TripsPagingAdapter(tripViewModel2, true)
        binding.recyclerViewTrips.adapter = tripsAdapter

        binding.progressBar.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    tripsViewModel.trips.collectLatest { pagingData ->
                        tripsAdapter.submitData(pagingData)
                    }

                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
