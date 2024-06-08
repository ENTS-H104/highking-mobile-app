package com.entsh104.highking.ui.cust.detailMountain

import UserRepository
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
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
import com.entsh104.highking.data.model.MountainDetailResponse
import com.entsh104.highking.data.model.OpenTrip

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
        userRepository = UserRepository(RetrofitClient.instance, prefs)

        fetchData(mountainId)
    }

    private fun fetchData(mountainId: String) {
        // Show ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            val fetchMountainDetailDeferred = async { fetchMountainDetail(mountainId) }
            val fetchTripsDeferred = async { fetchTripsForMountain(mountainId) }

            fetchMountainDetailDeferred.await()
            fetchTripsDeferred.await()

            // Hide ProgressBar
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

                // Handle "baca selengkapnya" click
                binding.tvReadMore.setOnClickListener {
                    // Logic to expand description
                }

                binding.llShareInfo.findViewById<View>(R.id.iv_twitter).setOnClickListener {
                    mountain?.let { shareInformation("twitter", it) }
                }
                binding.llShareInfo.findViewById<View>(R.id.iv_facebook).setOnClickListener {
                    mountain?.let { shareInformation("facebook", it) }
                }
                binding.llShareInfo.findViewById<View>(R.id.iv_instagram).setOnClickListener {
                    mountain?.let { shareInformation("instagram", it) }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Failed to load mountain details", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun fetchTripsForMountain(mountainId: String) {
        val result = userRepository.getOpenTrips()
        if (result.isSuccess) {
            val openTrips = result.getOrNull()?.data ?: emptyList()
            val tripsAdapter = TripsAdapter(openTrips, true)
            binding.rvSimilarTrips.adapter = tripsAdapter

            // Handle search trips button click
            binding.fabSearchTrips.setOnClickListener {
                findNavController().navigate(R.id.action_nav_detailMountain_to_nav_listTrip, null, NavOptionsUtil.defaultNavOptions)
            }

            binding.tvSeeAllTrips.setOnClickListener {
                findNavController().navigate(R.id.action_nav_detailMountain_to_nav_listTrip, null, NavOptionsUtil.defaultNavOptions)
            }
        } else {
            Toast.makeText(requireContext(), "Failed to load trips", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareInformation(platform: String, mountain: MountainDetailResponse) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out ${mountain.name} located at ${mountain.province} with an elevation of ${mountain.height} MDPL. Current weather: ${mountain.weather.cuaca}, Temperature: ${mountain.weather.temperature} °C. Entry fee: Rp ${mountain.harga}")
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
