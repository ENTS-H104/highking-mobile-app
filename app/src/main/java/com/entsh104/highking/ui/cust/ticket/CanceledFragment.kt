package com.entsh104.highking.ui.cust.ticket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.data.model.OpenTripDetail
import com.entsh104.highking.databinding.FragmentCustTicketListBinding
import com.entsh104.highking.data.model.TransactionHistory
import com.entsh104.highking.ui.adapters.OrdersAdapter
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class CanceledFragment : Fragment() {

    private var _binding: FragmentCustTicketListBinding? = null
    private val binding get() = _binding!!
    private var canceledOrders: List<TransactionHistory> = emptyList()
    private lateinit var adapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val jsonString = it.getString(ARG_ORDERS)
            val type = object : TypeToken<List<TransactionHistory>>() {}.type
            canceledOrders = Gson().fromJson(jsonString, type)
        }

        lifecycleScope.launch {
            val tripDetails = fetchTripDetailsForOrders(canceledOrders)
            adapter = OrdersAdapter(tripDetails, canceledOrders)
            binding.recyclerViewOrders.layoutManager = LinearLayoutManager(context)
            Log.d("CanceledFragmentTT", "onViewCreated: $canceledOrders")
            binding.recyclerViewOrders.adapter = adapter
        }
    }

    private suspend fun fetchTripDetailsForOrders(orders: List<TransactionHistory>): Map<String, OpenTripDetail> {
        val tripDetailsMap = mutableMapOf<String, OpenTripDetail>()
        val apiService = RetrofitClient.getInstance()

        for (order in orders) {
            val result = apiService.getOpenTripById(order.open_trip_uuid)
            if (result.isSuccessful) {
                result.body()?.data?.get(0)?.let { tripDetail ->
                    tripDetailsMap[order.open_trip_uuid] = tripDetail
                }
            }
        }

        return tripDetailsMap
    }

    fun updateData(newOrders: List<TransactionHistory>) {
        lifecycleScope.launch {
            val tripDetails = fetchTripDetailsForOrders(newOrders)
            canceledOrders = newOrders
            Log.d("CanceledFragmentTT", "updateData: $canceledOrders")
            adapter = OrdersAdapter(tripDetails, canceledOrders)
            binding.recyclerViewOrders.adapter = adapter
        }
    }

    companion object {
        private const val ARG_ORDERS = "orders"

        fun newInstance(orders: List<TransactionHistory>): CanceledFragment {
            val fragment = CanceledFragment()
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
