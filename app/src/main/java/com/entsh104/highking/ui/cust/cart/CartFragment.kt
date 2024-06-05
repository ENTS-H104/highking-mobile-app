package com.entsh104.highking.ui.cust.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCustCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup click listeners for buttons and other UI elements
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnCheckout.setOnClickListener {
            // Implement checkout functionality
        }

        binding.llPaymentMethod.setOnClickListener {
            // Implement navigation to payment method selection
        }

        binding.llDiscountCode.setOnClickListener {
            // Implement navigation to discount code entry
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
