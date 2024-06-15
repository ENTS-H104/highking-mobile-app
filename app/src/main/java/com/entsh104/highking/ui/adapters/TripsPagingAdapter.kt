// ui/adapters/TripsPagingAdapter.kt
package com.entsh104.highking.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.model.TripFilter
import java.text.DecimalFormat

class TripsPagingAdapter :
    PagingDataAdapter<TripFilter, TripsPagingAdapter.TripViewHolder>(TRIP_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = getItem(position)
        if (trip != null) {
            holder.bind(trip)
        }
    }

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewTrip)
        private val textViewTripName: TextView = itemView.findViewById(R.id.textViewTripName)
        private val textViewMountainName: TextView = itemView.findViewById(R.id.textViewMountainName)
        private val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        private val textViewCapacity: TextView = itemView.findViewById(R.id.textViewCapacity)

        fun bind(trip: TripFilter) {
            val mPrice = trip.price
            val mCurrencyFormat = DecimalFormat("#,###")
            val myFormattedPrice: String = mCurrencyFormat.format(mPrice)

            Glide.with(itemView.context).load(trip.image_url).into(imageView)
            textViewTripName.text = trip.name
            textViewMountainName.text = trip.mountain_name
            textViewPrice.text = "Rp $myFormattedPrice"
            textViewCapacity.text = "${trip.total_participants}"
        }
    }

    companion object {
        private val TRIP_COMPARATOR = object : DiffUtil.ItemCallback<TripFilter>() {
            override fun areItemsTheSame(oldItem: TripFilter, newItem: TripFilter): Boolean =
                oldItem.open_trip_uuid == newItem.open_trip_uuid

            override fun areContentsTheSame(oldItem: TripFilter, newItem: TripFilter): Boolean =
                oldItem == newItem
        }
    }
}
