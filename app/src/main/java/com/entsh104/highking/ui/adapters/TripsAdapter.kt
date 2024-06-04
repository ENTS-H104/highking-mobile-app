package com.entsh104.highking.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.R
import com.entsh104.highking.ui.model.Trip
import com.entsh104.highking.ui.cust.trip.ListTripFragmentDirections

class TripsAdapter(private val trips: List<Trip>, private val isHorizontal: Boolean = false) :
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

        fun bind(trip: Trip) {
            imageView.setImageResource(trip.imageResId)
            textViewTripName.text = trip.name
            textViewMountainName.text = trip.mountainName
            textViewPrice.text = trip.price
            textViewCapacity.text = trip.capacity.toString()
            buttonLove.setImageResource(
                if (trip.isLoved) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
            )
            buttonLove.setOnClickListener {
                // Handle love button click
                trip.isLoved = !trip.isLoved
                buttonLove.setImageResource(
                    if (trip.isLoved) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
                )
            }

            // Adjust item layout params for horizontal orientation
            if (isHorizontal) {
                val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.width = (itemView.context.resources.displayMetrics.widthPixels / 2) - 24 // Adjust the width to be half of the screen width with margin
                itemView.layoutParams = layoutParams
            }

            // Set click listener to navigate to detail trip
            itemView.setOnClickListener {
                val action = ListTripFragmentDirections.actionListTripToDetailTrip(trip)
                itemView.findNavController().navigate(action)
            }
        }
    }
}
