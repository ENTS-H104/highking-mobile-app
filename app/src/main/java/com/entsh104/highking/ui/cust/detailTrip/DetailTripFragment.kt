package com.entsh104.highking.ui.cust.detailTrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.entsh104.highking.databinding.FragmentCustDetailTripBinding

class DetailTripFragment : Fragment() {

    private var _binding: FragmentCustDetailTripBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustDetailTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve trip from arguments
        val trip = DetailTripFragmentArgs.fromBundle(requireArguments()).trip

        // Display trip details
        binding.ivFotoTrip.setImageResource(trip.imageResId)
        binding.tvNamaTrip.text = trip.name
        binding.tvHargaTrip.text = trip.price
        binding.tvPersonTrip.text = "${trip.capacity}/10"
        binding.tvNamaGunung.text = trip.mountainName
        // Update other views as needed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
