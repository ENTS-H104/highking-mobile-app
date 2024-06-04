package com.entsh104.highking.ui.adapters

import com.entsh104.highking.ui.model.Trip
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.R

class TripsAdapter(private val trips: List<Trip>) :
    RecyclerView.Adapter<TripsAdapter.TripsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false)
        return TripsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripsViewHolder, position: Int) {
        holder.bind(trips[position])
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    inner class TripsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_imageTrip)
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_namaTripCard)
        private val priceTextView: TextView = itemView.findViewById(R.id.tv_harga)

        fun bind(trip: Trip) {
            imageView.setImageResource(trip.imageResId)
            nameTextView.text = trip.name
            priceTextView.text = trip.price
        }
    }
}

