package com.entsh104.highking.ui.cust.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustTicketListBinding
import com.entsh104.highking.ui.adapters.OrdersAdapter
import com.entsh104.highking.ui.model.Order

class ActiveFragment : Fragment() {

    private var _binding: FragmentCustTicketListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewOrders.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewOrders.adapter = OrdersAdapter(getActiveOrders())
    }

    private fun getActiveOrders(): List<Order> {
        return listOf(
            Order(R.drawable.iv_trip, "Nama Pesanan 3", "Rp 10.000"),
            Order(R.drawable.iv_trip, "Nama Pesanan 4", "Rp 30.000"),
            Order(R.drawable.iv_trip, "Nama Pesanan 5", "Rp 20.000"),
            Order(R.drawable.iv_trip, "Nama Pesanan 6", "Rp 1.310.000"),
            Order(R.drawable.iv_trip, "Nama Pesanan 7", "Rp 120.000"),
            Order(R.drawable.iv_trip, "Nama Pesanan 8", "Rp 330.000")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
