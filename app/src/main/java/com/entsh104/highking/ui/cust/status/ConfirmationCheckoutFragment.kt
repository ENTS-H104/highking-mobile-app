package com.entsh104.highking.ui.cust.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustConfirmationCheckoutBinding
import com.entsh104.highking.ui.cust.CustActivity
import com.entsh104.highking.ui.util.NavOptionsUtil

class ConfirmationCheckoutFragment : Fragment() {

    private var _binding: FragmentCustConfirmationCheckoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustConfirmationCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener for back to history button
        binding.btnBackToHistory.setOnClickListener {
            findNavController().navigate(R.id.action_nav_confirmation_accepted_to_nav_orders, null, NavOptionsUtil.defaultNavOptions)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? CustActivity)?.hideToolbarAndNavbar()
    }

    override fun onPause() {
        super.onPause()
        (activity as? CustActivity)?.showToolbarAndNavbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
