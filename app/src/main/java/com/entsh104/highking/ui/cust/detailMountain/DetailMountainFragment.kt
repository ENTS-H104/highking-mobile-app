package com.entsh104.highking.ui.cust.detailMountain

import UserRepository
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustDetailMountainBinding
import com.entsh104.highking.ui.adapters.TripsAdapter
import com.entsh104.highking.ui.util.NavOptionsUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.entsh104.highking.data.helper.ViewModelFactory
import com.entsh104.highking.data.model.MountainDetailResponse
import com.entsh104.highking.data.viewmodel.FavoritesViewModel

class DetailMountainFragment : Fragment() {

    private var _binding: FragmentCustDetailMountainBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository
    private val args: DetailMountainFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustDetailMountainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mountainId = args.mountainId

        val prefs = SharedPreferencesManager(requireContext())
        RetrofitClient.createInstance(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        fetchData(mountainId)
    }

    private fun fetchData(mountainId: String) {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {

            val tripsLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
            binding.rvSimilarTrips.layoutManager = tripsLayoutManager

            val fetchMountainDetailDeferred = async { fetchMountainDetail(mountainId) }
            val fetchTripsDeferred = async { fetchTripsForMountain(mountainId) }

            fetchMountainDetailDeferred.await()
            fetchTripsDeferred.await()

            binding.progressBar.visibility = View.GONE
        }
    }

    private suspend fun fetchMountainDetail(mountainId: String) {
        val result = userRepository.getMountainById(mountainId)
        if (result.isSuccess) {
            val mountain = result.getOrNull()
            mountain?.let {
                Glide.with(this@DetailMountainFragment).load(it.imageUrl).into(binding.imageViewMountain)
                binding.textViewCityName.text = it.province
                binding.textViewMountainName.text = it.name
                binding.textViewElevation.text = "${it.height} MDPL"
                binding.textViewDescription.text = it.description
                binding.textViewWeather.text = "Cuaca Hari ini"
                binding.textViewWeatherDetail.text = it.weather.cuaca
                binding.textViewTemperatureLabel.text = "Suhu Derajat"
                binding.textViewTemperature.text = "${it.weather.temperature} Celcius"
                binding.textViewFeeLabel.text = "Harga Masuk"
                binding.textViewTicketPrice.text = "Rp ${it.harga}"
                binding.tvSimilarTripsHeader.text = "Trip di ${it.name}"

                binding.tvReadMore.setOnClickListener {
                    // Handle read more click
                }

                binding.llShareInfo.findViewById<View>(R.id.iv_twitter).setOnClickListener {
                    shareInformation("twitter", mountain)
                }
                binding.llShareInfo.findViewById<View>(R.id.iv_facebook).setOnClickListener {
                    shareInformation("facebook", mountain)
                }
                binding.llShareInfo.findViewById<View>(R.id.iv_instagram).setOnClickListener {
                    shareInformation("instagram", mountain)
                }
            }
        } else {
            Toast.makeText(requireContext(), "Failed to load mountain details", Toast.LENGTH_SHORT).show()
        }
    }


    private suspend fun fetchTripsForMountain(mountainId: String) {
        val apiService = RetrofitClient.getInstance()
        val result = apiService.searchOpenTrip(mountainId, "2024")
        if (result.isSuccessful && result.body() != null) {
            val searchResults = result.body()?.data ?: emptyList()
            val favoriteViewModel = obtainViewModel(requireActivity())

            val tripsAdapter = TripsAdapter(searchResults, true, favoriteViewModel)
            binding.rvSimilarTrips.adapter = tripsAdapter

            binding.fabSearchTrips.setOnClickListener {
                val action = DetailMountainFragmentDirections.actionNavDetailMountainToNavListTrip(
                    searchResults?.toTypedArray() ?: emptyArray()
                )
                findNavController().navigate(action)
            }

            binding.tvSeeAllTrips.setOnClickListener {
                val action = DetailMountainFragmentDirections.actionNavDetailMountainToNavListTrip(
                    searchResults?.toTypedArray() ?: emptyArray()
                )
                findNavController().navigate(action,  NavOptionsUtil.defaultNavOptions)
            }
        } else {
            Toast.makeText(requireContext(), "Failed to load trips", Toast.LENGTH_SHORT).show()
        }
    }
    private fun obtainViewModel(activity: FragmentActivity): FavoritesViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoritesViewModel::class.java]
    }

    fun shareInformation(platform: String, detail: MountainDetailResponse) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out ${detail.name} located at ${detail.province} with an elevation of ${detail.height} MDPL. Current weather: ${detail.weather.cuaca}, Temperature: ${detail.weather.temperature} °C. Entry fee: Rp ${detail.harga}")
            type = "text/plain"
        }

        when (platform) {
            "twitter" -> shareIntent.setPackage("com.twitter.android")
            "facebook" -> shareIntent.setPackage("com.facebook.katana")
            "instagram" -> shareIntent.setPackage("com.instagram.android")
        }

        try {
            startActivity(shareIntent)
        } catch (e: Exception) {
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
