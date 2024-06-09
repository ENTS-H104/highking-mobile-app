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
import com.entsh104.highking.data.model.TransactionHistory
import com.entsh104.highking.ui.cust.ticket.TicketFragmentDirections
import com.entsh104.highking.ui.util.NavOptionsUtil

class OrdersAdapter(private val orders: List<TransactionHistory>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewOrder)
        private val textViewOrderName: TextView = itemView.findViewById(R.id.textViewOrderName)
        private val textViewPrice: TextView = itemView.findViewById(R.id.textViewOrderPrice)

        fun bind(order: TransactionHistory) {
            // Assuming you have an image URL in your TransactionHistory model
//            Glide.with(itemView.context)
//                .load(order.imageUrl)  // You'll need to add this field to your model
//                .placeholder(R.drawable.placeholder_image)  // Add a placeholder image
//                .into(imageView)

            textViewOrderName.text = order.open_trip_uuid
            textViewPrice.text = "Rp ${order.amount_paid}"

            itemView.setOnClickListener {
                val action = TicketFragmentDirections.actionNavOrdersToOrderDetailsFragment(order.transaction_logs_uuid)
                it.findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
            }
        }
    }
}
