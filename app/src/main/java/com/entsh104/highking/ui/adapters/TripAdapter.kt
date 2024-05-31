package com.entsh104.highking.ui.adapters

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.ui.model.Trip

class TripAdapter(trips: List<Trip>) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {
    private val trips: List<Trip>

    init {
        this.trips = trips
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip: Trip = trips[position]
        holder.imageView.setImageResource(trip.getImageResId())
        holder.textViewName.setText(trip.getName())
        holder.textViewPrice.setText(trip.getPrice())
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    internal class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textViewName: TextView
        var textViewPrice: TextView

        init {
            imageView = itemView.findViewById<ImageView>(R.id.imageViewTrip)
            textViewName = itemView.findViewById<TextView>(R.id.textViewTripName)
            textViewPrice = itemView.findViewById<TextView>(R.id.textViewTripPrice)
        }
    }
}
