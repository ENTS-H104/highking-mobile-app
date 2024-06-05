package com.entsh104.highking.ui.cust.detailTrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
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
        binding.ivTripImage.setImageResource(trip.imageResId)
        binding.tvTripName.text = trip.name
        binding.tvTripPrice.text = trip.price
        binding.tvTripAvailability.text = "${trip.capacity}/20"
        binding.tvTripLocation.text = trip.mountainName
        // Update other views as needed

        // Toggle inclusion section
        binding.tvInclusion.setOnClickListener {
            toggleSection(binding.llInclusionContent, binding.ivExpandInclusion)
        }

        // Toggle exclusion section
        binding.tvExclusion.setOnClickListener {
            toggleSection(binding.llExclusionContent, binding.ivExpandExclusion)
        }

        // Toggle day 1 section
        binding.tvDay1.setOnClickListener {
            toggleSection(binding.llDay1Content, binding.ivExpandDay1)
        }

        // Toggle day 2 section
        binding.tvDay2.setOnClickListener {
            toggleSection(binding.llDay2Content, binding.ivExpandDay2)
        }

        // Toggle FAQ 1 section
        binding.tvFaq1.setOnClickListener {
            toggleSection(binding.llFaq1Content, binding.ivExpandFaq1)
        }

        // Toggle FAQ 2 section
        binding.tvFaq2.setOnClickListener {
            toggleSection(binding.llFaq2Content, binding.ivExpandFaq2)
        }

        binding.fabCheckoutTrip.setOnClickListener {
            findNavController().navigate(R.id.action_nav_detailTrip_to_cartFragment)
        }
    }

    private fun toggleSection(contentLayout: LinearLayout, arrowView: ImageView) {
        if (contentLayout.visibility == View.GONE) {
            contentLayout.visibility = View.VISIBLE
            arrowView.setImageResource(R.drawable.ic_arrow_up)
        } else {
            contentLayout.visibility = View.GONE
            arrowView.setImageResource(R.drawable.ic_arrow_down)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
