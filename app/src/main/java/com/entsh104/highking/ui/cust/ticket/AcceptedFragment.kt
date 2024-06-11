package com.entsh104.highking.ui.cust.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.databinding.FragmentCustTicketListBinding
import com.entsh104.highking.data.model.TransactionHistory
import com.entsh104.highking.ui.adapters.OrdersAdapter

class AcceptedFragment : Fragment() {

    private var _binding: FragmentCustTicketListBinding? = null
    private val binding get() = _binding!!
    private lateinit var acceptedOrders: List<TransactionHistory>

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
        binding.recyclerViewOrders.adapter = OrdersAdapter(acceptedOrders)
    }

    companion object {
        fun newInstance(orders: List<TransactionHistory>): AcceptedFragment {
            val fragment = AcceptedFragment()
            fragment.acceptedOrders = orders
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
