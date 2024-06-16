package com.entsh104.highking.ui.cust.ticket

import UserRepository
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.data.model.OpenTripDetail
import com.entsh104.highking.databinding.FragmentCustTicketListBinding
import com.entsh104.highking.data.model.TransactionHistory
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.ui.adapters.OrdersAdapter
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AcceptedFragment : Fragment() {

    private var _binding: FragmentCustTicketListBinding? = null
    private val binding get() = _binding!!
    private var acceptedOrders: List<TransactionHistory> = emptyList()
    private lateinit var adapter: OrdersAdapter

    private lateinit var userRepository: UserRepository
    private lateinit var prefs: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = SharedPreferencesManager(requireContext())
        RetrofitClient.createInstance(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        arguments?.let {
            val jsonString = it.getString(ARG_ORDERS)
            val type = object : TypeToken<List<TransactionHistory>>() {}.type
            acceptedOrders = Gson().fromJson(jsonString, type)
        }

        binding.progressBar.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    val tripDetails = fetchTripDetailsForOrders(acceptedOrders)
                    adapter = OrdersAdapter(tripDetails, acceptedOrders)
                    binding.recyclerViewOrders.layoutManager = LinearLayoutManager(context)
                    binding.recyclerViewOrders.adapter = adapter
                }
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private suspend fun fetchTripDetailsForOrders(orders: List<TransactionHistory>): Map<String, OpenTripDetail> {
        val tripDetailsMap = mutableMapOf<String, OpenTripDetail>()
        val apiService = RetrofitClient.getInstance()

        for (order in orders) {
            val openTripId = order.open_trip_uuid
            val userRepository = UserRepository(apiService, prefs)
            val result = userRepository.getOpenTripById(openTripId)
            if (result.isSuccess) {
                val tripList = result.getOrNull()?.data
                tripList?.firstOrNull()?.let { trip ->
                    tripDetailsMap[order.transaction_logs_uuid] = trip
                }
            }
        }

        return tripDetailsMap
    }

    fun updateData(newOrders: List<TransactionHistory>) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    val tripDetails = fetchTripDetailsForOrders(newOrders)
                    acceptedOrders = newOrders
                    adapter = OrdersAdapter(tripDetails, acceptedOrders)
                    binding.recyclerViewOrders.adapter = adapter
                }
            }
        }
    }

    companion object {
        private const val ARG_ORDERS = "orders"

        fun newInstance(orders: List<TransactionHistory>): AcceptedFragment {
            val fragment = AcceptedFragment()
            val args = Bundle()
            val jsonString = Gson().toJson(orders)
            args.putString(ARG_ORDERS, jsonString)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
