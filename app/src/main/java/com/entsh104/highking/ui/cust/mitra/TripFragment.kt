package com.entsh104.highking.ui.cust.trip

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.entsh104.highking.data.helper.ViewModelFactory
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.data.viewmodel.TripViewModel
import com.entsh104.highking.databinding.FragmentCustMitraProfileTripBinding
import com.entsh104.highking.ui.adapters.TripsAdapter
import kotlinx.coroutines.launch

class TripFragment : Fragment() {

    private var _binding: FragmentCustMitraProfileTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository
    private lateinit var mitraId: String
    private val tripViewModel: TripViewModel by viewModels()

    companion object {
        private const val ARG_MITRA_ID = "mitra_id"

        fun newInstance(mitraId: String): TripFragment {
            val fragment = TripFragment()
            val args = Bundle()
            args.putString(ARG_MITRA_ID, mitraId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mitraId = it.getString(ARG_MITRA_ID) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustMitraProfileTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = SharedPreferencesManager(requireContext())
        RetrofitClient.createInstance(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        setupRecyclerView()
        fetchTrips()
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewTrip.layoutManager = gridLayoutManager
        binding.recyclerViewTrip.addItemDecoration(GridSpacingItemDecoration(2, 1, true))
    }

    private fun fetchTrips() {
        // Show ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            val result = userRepository.getMitraTrips(mitraId)
            if (result.isSuccess) {
                val openTrips = result.getOrNull() ?: emptyList()

                val tripsAdapter = TripsAdapter(openTrips, true, tripViewModel)
                binding.recyclerViewTrip.adapter = tripsAdapter
            } else {
                Toast.makeText(requireContext(), "Failed to load trips", Toast.LENGTH_SHORT).show()
            }

            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
