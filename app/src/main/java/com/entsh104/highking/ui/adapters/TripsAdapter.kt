// TripsAdapter.kt
package com.entsh104.highking.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.model.TripFilter
import com.entsh104.highking.ui.cust.trip.ListTripFragmentDirections
import com.entsh104.highking.ui.util.NavOptionsUtil

class TripsAdapter(private val trips: List<TripFilter>, private val isHorizontal: Boolean = false) :
    RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view, isHorizontal)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(trips[position])
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    inner class TripViewHolder(itemView: View, private val isHorizontal: Boolean) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewTrip)
        private val buttonLove: ImageButton = itemView.findViewById(R.id.buttonLove)
        private val textViewTripName: TextView = itemView.findViewById(R.id.textViewTripName)
        private val textViewMountainName: TextView = itemView.findViewById(R.id.textViewMountainName)
        private val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        private val textViewCapacity: TextView = itemView.findViewById(R.id.textViewCapacity)

        fun bind(trip: TripFilter) {
            Glide.with(itemView.context).load(trip.image_url).into(imageView)
            textViewTripName.text = trip.name
            textViewMountainName.text = trip.mountain_name
            textViewPrice.text = "Rp ${trip.price}"
            textViewCapacity.text = "${trip.total_participants}"

            // Adjust item layout params for horizontal orientation
            if (isHorizontal) {
                val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.width = (itemView.context.resources.displayMetrics.widthPixels / 2) - 24
                itemView.layoutParams = layoutParams
            }

            // Set click listener to navigate to detail trip
            itemView.setOnClickListener {
                val action = ListTripFragmentDirections.actionListTripToDetailTrip(trip.open_trip_uuid)
                itemView.findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
            }
        }
    }
}
