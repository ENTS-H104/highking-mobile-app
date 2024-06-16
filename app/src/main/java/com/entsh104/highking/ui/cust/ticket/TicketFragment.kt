package com.entsh104.highking.ui.cust.ticket

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.entsh104.highking.R
import com.entsh104.highking.data.model.TransactionHistory
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustTicketBinding
import com.entsh104.highking.ui.ticket.TicketPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class TicketFragment : Fragment() {

    private var _binding: FragmentCustTicketBinding? = null
    private val binding get() = _binding!!
    private lateinit var ticketPagerAdapter: TicketPagerAdapter
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = SharedPreferencesManager(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketPagerAdapter = TicketPagerAdapter(this)
        binding.viewPager.adapter = ticketPagerAdapter

        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Pending"
                1 -> "Accepted"
                2 -> "Canceled"
                else -> null
            }
        }.attach()

        fetchAllTransactionHistories()
    }

    override fun onResume() {
        super.onResume()
        ticketPagerAdapter = TicketPagerAdapter(this)
        binding.viewPager.adapter = ticketPagerAdapter

        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Pending"
                1 -> "Accepted"
                2 -> "Canceled"
                else -> null
            }
        }.attach()

        fetchAllTransactionHistories()
    }

    private fun fetchAllTransactionHistories() {
        fetchTransactionHistories("PENDING") { pendingTransactions ->
            ticketPagerAdapter.setPendingTransactions(pendingTransactions)
        }
        fetchTransactionHistories("ACCEPTED") { acceptedTransactions ->
            ticketPagerAdapter.setAcceptedTransactions(acceptedTransactions)
        }
        fetchTransactionHistories("CANCELED") { canceledTransactions ->
            ticketPagerAdapter.setCanceledTransactions(canceledTransactions)
        }
    }

    private fun fetchTransactionHistories(status: String, callback: (List<TransactionHistory>) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            val userResult = userRepository.getCurrentUser()
            if (userResult.isSuccess) {
                val userId = userRepository.getCurrentUserId()
                if (userId != null) {
                    try {
                        val apiService = RetrofitClient.getInstance()
                        val response = apiService.getTransactionHistories(userId, status)
                        if (response.isSuccessful) {
                            val transactionHistories = response.body()?.data ?: emptyList()
                            callback(transactionHistories)
                        } else {
                            // Handle the error
                        }
                    } catch (e: Exception) {
                        // Handle the error
                    }
                } else {
                    // Handle the case when userId is null
                }
            } else {
                // Handle the case when fetching current user fails
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
