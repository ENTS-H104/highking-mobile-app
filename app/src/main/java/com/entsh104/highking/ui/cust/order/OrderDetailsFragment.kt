package com.entsh104.highking.ui.cust.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustOrderDetailsBinding
import com.entsh104.highking.ui.util.NavOptionsUtil

class OrderDetailsFragment : Fragment() {

    private var _binding: FragmentCustOrderDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up view with order details
        setupOrderDetails()

        // Set up print ticket button
        binding.btnPrintTicket.setOnClickListener {
            printTicket()
        }

        binding.btnBack.setOnClickListener {
            val action = OrderDetailsFragmentDirections.actionOrderDetailsFragmentToNavOrders()
            it.findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
        }
    }

    private fun setupOrderDetails() {
        binding.imageViewMountain.setImageResource(R.drawable.iv_mountain)
        binding.textViewTripName.text = "TRIP MURMER - Pendakian Gunung Pundak"
        binding.textViewHikerName.text = "CATHALINA ANGELINE"
        binding.textViewDepartureDate.text = "11 MEI 2024; 07:00 WIB"
        binding.textViewPickupLocation.text = "TERMINAL BUS MOJOKERTO"
        binding.textViewContactInfo.text = "+62 855 1234 5678"
    }

    private fun printTicket() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
