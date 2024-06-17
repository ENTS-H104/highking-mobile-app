package com.entsh104.highking.ui.cust.beranda

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.R
import com.entsh104.highking.data.helper.ViewModelFactory
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.data.viewmodel.MountainViewModel
import com.entsh104.highking.data.viewmodel.TripViewModel
import com.entsh104.highking.databinding.FragmentCustBerandaBinding
import com.entsh104.highking.ui.adapters.BannerAdapter
import com.entsh104.highking.ui.adapters.MountainAdapter
import com.entsh104.highking.ui.adapters.TripsAdapter
import com.entsh104.highking.ui.model.Banner
import com.entsh104.highking.ui.util.NavOptionsUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BerandaFragment : Fragment() {

    private var _binding: FragmentCustBerandaBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository
    private val mountainViewModel: MountainViewModel by viewModels()
    private val tripViewModel: TripViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = SharedPreferencesManager(requireContext())
        RetrofitClient.createInstance(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        setupRecyclerViews()
        fetchData()
    }

    private fun setupRecyclerViews() {
        binding.recyclerViewBanner.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMountains.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val tripsLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTrips.layoutManager = tripsLayoutManager

        val bannerAdapter = BannerAdapter(getBannerData())
        binding.recyclerViewBanner.adapter = bannerAdapter
    }

    private fun fetchData() {
        binding.shimmerViewContainer.startShimmer()

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    val userResult = userRepository.getCurrentUser()
                    if (userResult.isSuccess) {
                        val userId = userRepository.getCurrentUserId()
                            if(userId != null) {
                                val fetchMountainsDeferred = async { fetchRecommendedMountains(userId) }
                                val fetchOpenTripsDeferred = async { fetchRecommendedTrips(userId) }

                                fetchMountainsDeferred.await()
                                fetchOpenTripsDeferred.await()

                                binding.shimmerViewContainer.stopShimmer()
                                binding.shimmerViewContainer.visibility = View.GONE
                                binding.contentView.visibility = View.VISIBLE
                        }
                    } else {
                        // Handle the case when fetching current user fails
                    }
                }
            }
        }
    }

    private suspend fun fetchRecommendedMountains(userId: String) {
        val result = userRepository.getRecommendedMountains(userId)
        if (result.isSuccess) {
            val mountains = result.getOrNull() ?: emptyList()
            val mountainsAdapter = MountainAdapter(mountains, mountainViewModel, true)
            binding.recyclerViewMountains.adapter = mountainsAdapter

            binding.gunungTerpopulerLihatSemua.setOnClickListener {
                val action = BerandaFragmentDirections.actionHomeToListMountain(mountains.toTypedArray())
                findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
            }
            binding.llJelajahiSemuaGunung.setOnClickListener {
                val action = BerandaFragmentDirections.actionHomeToListAllMountain()
                findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
            }

        } else {
            Toast.makeText(requireContext(), "Failed to load recommended mountains", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun fetchRecommendedTrips(userId: String) {
        val apiService = RetrofitClient.getInstance()
        val result = apiService.getRecommendedTrips(userId)
        if (result.isSuccessful && result.body() != null) {
            val trips = result.body()?.data ?: emptyList()
            val tripsAdapter = TripsAdapter(trips, true, tripViewModel)
            binding.recyclerViewTrips.adapter = tripsAdapter

            binding.rekomendasiTripLihatSemua.setOnClickListener {
                val action = BerandaFragmentDirections.actionHomeToListTrip(trips.toTypedArray())
                findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
            }

            binding.llTemukanTripTerdekat.setOnClickListener {
                val action = BerandaFragmentDirections.actionHomeToAllListTrip()
                findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
            }

        } else {
            Toast.makeText(requireContext(), "Failed to load recommended trips", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getBannerData(): List<Banner> {
        return listOf(
            Banner(R.drawable.iv_banner)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
