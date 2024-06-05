package com.entsh104.highking.ui.cust.detailMountain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustDetailMountainBinding
import com.entsh104.highking.ui.adapters.TripsAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.entsh104.highking.ui.model.Trip
import com.entsh104.highking.ui.util.NavOptionsUtil

class DetailMountainFragment : Fragment() {

    private var _binding: FragmentCustDetailMountainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustDetailMountainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve mountain from arguments
        val mountain = DetailMountainFragmentArgs.fromBundle(requireArguments()).mountain

        // Display mountain details
        binding.imageViewMountain.setImageResource(mountain.imageResId)
        binding.textViewCityName.text = mountain.location
        binding.textViewMountainName.text = mountain.name
        binding.textViewElevation.text = "${mountain.elevation} MDPL"
        binding.textViewDescription.text = mountain.description
        binding.textViewWeather.text = "Cuaca Hari ini"
        binding.textViewWeatherDetail.text = mountain.weather
        binding.textViewTemperatureLabel.text = "Suhu Derajat"
        binding.textViewTemperature.text = "${mountain.temperature} Celcius"
        binding.textViewFeeLabel.text = "Harga Masuk"
        binding.textViewTicketPrice.text = "Rp ${mountain.entryFee}"
        binding.tvSimilarTripsHeader.text = "Trip di ${mountain.name}"

        // Handle "baca selengkapnya" click
        binding.tvReadMore.setOnClickListener {
            // Logic to expand description
        }

        val similarTripsAdapter = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        binding.rvSimilarTrips.layoutManager = similarTripsAdapter

        val tripsAdapter = TripsAdapter(getTripsData(), true)
        binding.rvSimilarTrips.adapter = tripsAdapter

        // Handle search trips button click
        binding.fabSearchTrips.setOnClickListener {
            findNavController().navigate(R.id.action_nav_detailMountain_to_nav_listTrip, null, NavOptionsUtil.defaultNavOptions)
        }

        binding.tvSeeAllTrips.setOnClickListener {
            findNavController().navigate(R.id.action_nav_detailMountain_to_nav_listTrip, null, NavOptionsUtil.defaultNavOptions)
        }
    }

    private fun getTripsData(): List<Trip> {
        // Replace with your actual data fetching logic
        return listOf(
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", false, 99),
            Trip(R.drawable.iv_trip, "Trip Kencana", "Rinjani", "Rp 150.000", true, 99)
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
