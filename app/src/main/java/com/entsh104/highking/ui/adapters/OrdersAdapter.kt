package com.entsh104.highking.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.model.OpenTripDetail
import com.entsh104.highking.data.model.TransactionHistory
import com.entsh104.highking.ui.cust.ticket.TicketFragmentDirections
import com.entsh104.highking.ui.util.NavOptionsUtil

class OrdersAdapter(
    private val orders: Map<String, OpenTripDetail>,
    pendingOrders: List<TransactionHistory>
) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders.values.elementAt(position))
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewOrder)
        private val textViewOrderName: TextView = itemView.findViewById(R.id.textViewOrderName)
        private val textViewPrice: TextView = itemView.findViewById(R.id.textViewOrderPrice)

        fun bind(order: OpenTripDetail?) {
            Glide.with(itemView.context)
                .load(order?.image_url)
                .into(imageView)

            textViewOrderName.text = order?.open_trip_name
            textViewPrice.text = "Rp ${order?.price}"

            itemView.setOnClickListener {
                if (order != null) {
                    val action = TicketFragmentDirections.actionNavOrdersToOrderDetailsFragment(order)
                    it.findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
                }
            }
        }
    }
}
