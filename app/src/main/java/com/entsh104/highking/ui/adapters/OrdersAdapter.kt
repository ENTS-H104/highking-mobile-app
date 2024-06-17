package com.entsh104.highking.ui.adapters

import android.util.Log
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
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class OrdersAdapter(
    private val orders: Map<String, OpenTripDetail>,
    Orders: List<TransactionHistory>
) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders.keys.elementAt(position), orders.values.elementAt(position))
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewOrder)
        private val textViewOrderName: TextView = itemView.findViewById(R.id.textViewOrderName)
        private val textViewPrice: TextView = itemView.findViewById(R.id.textViewOrderPrice)

        fun bind(transactionId: String, order: OpenTripDetail?) {
            Glide.with(itemView.context)
                .load(order?.image_url)
                .into(imageView)

            textViewOrderName.text = order?.open_trip_name

            val mPrice = order?.price
            val symbols = DecimalFormatSymbols(Locale.getDefault())
            symbols.groupingSeparator = '.'
            val mCurrencyFormat = DecimalFormat("#,###", symbols)
            val myFormattedPrice: String = mCurrencyFormat.format(mPrice)
            textViewPrice.text = "Rp $myFormattedPrice"

            itemView.setOnClickListener {
                if (order != null) {
                    val action =
                        TicketFragmentDirections.actionNavOrdersToOrderDetailsFragment(transactionId, order)
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}
